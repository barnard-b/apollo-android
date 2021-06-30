package com.apollographql.apollo3.interceptor.cache.internal

import com.apollographql.apollo3.ClientScope
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.CustomScalarAdapters
import com.apollographql.apollo3.api.ExecutionContext
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Query
import com.apollographql.apollo3.api.Subscription
import com.apollographql.apollo3.exception.ApolloCompositeException
import com.apollographql.apollo3.cache.CacheHeaders
import com.apollographql.apollo3.cache.normalized.ApolloStore
import com.apollographql.apollo3.cache.normalized.internal.dependentKeys
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.cache.CACHE_FLAG_DO_NOT_STORE
import com.apollographql.apollo3.interceptor.cache.CACHE_FLAG_STORE_PARTIAL_RESPONSE
import com.apollographql.apollo3.interceptor.cache.FetchPolicy
import com.apollographql.apollo3.interceptor.cache.isFromCache
import com.apollographql.apollo3.mpp.ensureNeverFrozen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

internal class ApolloCacheInterceptor(
    private val store: ApolloStore,
    private val writeToCacheAsynchronously: Boolean,
) : ApolloInterceptor {
  private suspend fun maybeAsync(executionContext: ExecutionContext, block: suspend () -> Unit) {
    val coroutineScope = executionContext[ClientScope]?.coroutineScope
    if (writeToCacheAsynchronously && coroutineScope != null) {
      coroutineScope.launch { block() }
    } else {
      block()
    }
  }

  private suspend fun <D : Operation.Data> maybeWriteToCache(
      request: ApolloRequest<D>,
      response: ApolloResponse<D>,
      customScalarAdapters: CustomScalarAdapters,
      cacheContext: CacheContext,
      extraKeys: Set<String> = emptySet()
  ) {
    if (cacheContext.flags.and(CACHE_FLAG_DO_NOT_STORE) != 0) {
      return
    }
    if (response.hasErrors() && cacheContext.flags.and(CACHE_FLAG_STORE_PARTIAL_RESPONSE) == 0) {
      return
    }

    maybeAsync(request.executionContext) {
      val cacheKeys = if (!response.isFromCache && response.data != null) {
        store.writeOperation(request.operation, response.data!!, customScalarAdapters, cacheContext.cacheHeaders, publish = false)
      } else {
        emptySet()
      }
      store.publish(cacheKeys + extraKeys)
    }
  }


  override fun <D : Operation.Data> intercept(request: ApolloRequest<D>, chain: ApolloInterceptorChain): Flow<ApolloResponse<D>> {
    val cacheContext = request.executionContext[CacheContext] ?: DefaultCacheContext(request.operation)
    val fetchPolicy = cacheContext.fetchPolicy
    val refetchPolicy = cacheContext.refetchPolicy
    val customScalarAdapters = request.executionContext[CustomScalarAdapters]!!

    if (request.operation is Subscription) {
      return proceed(request, chain).onEach { response ->
        maybeWriteToCache(request, response, customScalarAdapters, cacheContext)
      }
    }

    return flow {
      var result = kotlin.runCatching {
        @Suppress("UNCHECKED_CAST")
        fetchOne(request, chain, fetchPolicy, customScalarAdapters, cacheContext)
      }
      val response = result.getOrNull()

      if (response != null) {
        emit(response)
      }

      if (refetchPolicy == null) {
        if (result.isFailure) {
          throw result.exceptionOrNull()!!
        }
        return@flow
      }

      var watchedKeys = if (response != null && !response.hasErrors() && response.data != null) {
        store.normalize(request.operation, response.data!!, customScalarAdapters).values.dependentKeys()
      } else {
        null
      }

      store.changedKeys.collect { changedKeys ->
        if (watchedKeys == null || changedKeys.intersect(watchedKeys!!).isNotEmpty()) {
          result = kotlin.runCatching {
            fetchOne(request, chain, refetchPolicy, customScalarAdapters, cacheContext)
          }

          val newResponse = result.getOrNull()
          if (newResponse != null) {
            emit(newResponse)

            if (!newResponse.hasErrors() && newResponse.data != null) {
              watchedKeys = store.normalize(request.operation, newResponse.data!!, customScalarAdapters).values.dependentKeys()
            }
          }
        }
      }
    }
  }

  private suspend fun <D : Operation.Data> fetchOne(
      request: ApolloRequest<D>,
      chain: ApolloInterceptorChain,
      fetchPolicy: FetchPolicy,
      customScalarAdapters: CustomScalarAdapters,
      cacheContext: CacheContext,
  ): ApolloResponse<D> {

    if (cacheContext.optimisticData != null) {
      store.writeOptimisticUpdates(
          operation = request.operation,
          operationData = cacheContext.optimisticData as D,
          mutationId = request.requestUuid,
          customScalarAdapters = customScalarAdapters,
          publish = true
      )
    }

    val result = kotlin.runCatching {
      fetchOneMightThrow(request, chain, fetchPolicy, customScalarAdapters, cacheContext)
    }

    if (result.isSuccess) {
      val response = result.getOrThrow()

      val optimisticKeys = if (cacheContext.optimisticData != null) {
        store.rollbackOptimisticUpdates(request.requestUuid, publish = false)
      } else {
        emptySet()
      }
      maybeWriteToCache(request, response, customScalarAdapters, cacheContext, optimisticKeys)
    }

    return result.getOrThrow()
  }

  private suspend fun <D : Operation.Data> fetchOneMightThrow(
      request: ApolloRequest<D>,
      chain: ApolloInterceptorChain,
      fetchPolicy: FetchPolicy,
      customScalarAdapters: CustomScalarAdapters,
      cacheContext: CacheContext,
  ): ApolloResponse<D> {
    when (fetchPolicy) {
      FetchPolicy.CacheFirst -> {
        ensureNeverFrozen(store)
        val cacheResult = kotlin.runCatching {
          readFromCache(request, customScalarAdapters, cacheContext)
        }

        val cacheResponse = cacheResult.getOrNull()
        if (cacheResponse != null) {
          return cacheResponse
        }

        val networkResult = kotlin.runCatching {
          proceed(request, chain).single()
        }

        val networkResponse = networkResult.getOrNull()
        if (networkResponse != null) {
          return networkResponse
        }

        throw ApolloCompositeException(
            cacheResult.exceptionOrNull(),
            networkResult.exceptionOrNull()
        )
      }
      FetchPolicy.NetworkFirst -> {
        val networkResult = kotlin.runCatching {
          proceed(request, chain).single()
        }

        val networkResponse = networkResult.getOrNull()
        if (networkResponse != null) {
          return networkResponse
        }

        val cacheResult = kotlin.runCatching {
          readFromCache(request, customScalarAdapters, cacheContext)
        }

        val cacheResponse = cacheResult.getOrNull()
        if (cacheResponse != null) {
          return cacheResponse
        }

        throw ApolloCompositeException(
            networkResult.exceptionOrNull(),
            cacheResult.exceptionOrNull(),
        )
      }
      FetchPolicy.CacheOnly -> {
        return readFromCache(request, customScalarAdapters, cacheContext)
      }
      FetchPolicy.NetworkOnly -> {
        return proceed(request, chain).single()
      }
    }
  }

  private fun <D : Operation.Data> proceed(request: ApolloRequest<D>, chain: ApolloInterceptorChain): Flow<ApolloResponse<D>> {
    return chain.proceed(request).map {
      it.setFromCache(false)
    }
  }

  private fun <D : Operation.Data> ApolloResponse<D>.setFromCache(fromCache: Boolean): ApolloResponse<D> {
    return copy(executionContext = executionContext + CacheOutput(fromCache))
  }

  private suspend fun <D : Operation.Data> readFromCache(
      request: ApolloRequest<D>,
      customScalarAdapters: CustomScalarAdapters,
      cacheContext: CacheContext
  ): ApolloResponse<D> {
    val operation = request.operation

    val data = store.readOperation(
        operation = operation,
        customScalarAdapters = customScalarAdapters,
        cacheHeaders = cacheContext.cacheHeaders
    )

    return ApolloResponse(
        requestUuid = request.requestUuid,
        operation = operation,
        data = data,
        executionContext = request.executionContext + CacheOutput(true)
    )
  }
}