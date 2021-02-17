// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.root_query_fragment_with_nested_fragments.adapter

import com.apollographql.apollo3.api.ResponseAdapterCache
import com.apollographql.apollo3.api.ResponseField
import com.apollographql.apollo3.api.internal.NullableResponseAdapter
import com.apollographql.apollo3.api.internal.ResponseAdapter
import com.apollographql.apollo3.api.internal.StringResponseAdapter
import com.apollographql.apollo3.api.internal.json.JsonReader
import com.apollographql.apollo3.api.internal.json.JsonWriter
import com.example.root_query_fragment_with_nested_fragments.TestQuery
import kotlin.Array
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
class TestQuery_ResponseAdapter(
  responseAdapterCache: ResponseAdapterCache
) : ResponseAdapter<TestQuery.Data> {
  val QueryDataAdapter: QueryData =
      com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.QueryData(responseAdapterCache)

  val OtherDataAdapter: OtherData =
      com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.OtherData(responseAdapterCache)

  override fun fromResponse(reader: JsonReader): TestQuery.Data {
    reader.beginObject()
    check(reader.nextName() == "__typename")
    val typename = reader.nextString()

    return when(typename) {
      "Query" -> QueryDataAdapter.fromResponse(reader, typename)
      else -> OtherDataAdapter.fromResponse(reader, typename)
    }
    .also { reader.endObject() }
  }

  override fun toResponse(writer: JsonWriter, value: TestQuery.Data) {
    when(value) {
      is TestQuery.Data.QueryData -> QueryDataAdapter.toResponse(writer, value)
      is TestQuery.Data.OtherData -> OtherDataAdapter.toResponse(writer, value)
    }
  }

  class QueryData(
    responseAdapterCache: ResponseAdapterCache
  ) {
    private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

    private val nullableHeroAdapter: ResponseAdapter<TestQuery.Data.QueryData.Hero?> =
        NullableResponseAdapter(Hero(responseAdapterCache))

    private val nullableDroidAdapter: ResponseAdapter<TestQuery.Data.QueryData.Droid?> =
        NullableResponseAdapter(Droid(responseAdapterCache))

    private val nullableHumanAdapter: ResponseAdapter<TestQuery.Data.QueryData.Human?> =
        NullableResponseAdapter(Human(responseAdapterCache))

    fun fromResponse(reader: JsonReader, __typename: String?): TestQuery.Data.QueryData {
      var __typename: String? = __typename
      var hero: TestQuery.Data.QueryData.Hero? = null
      var droid: TestQuery.Data.QueryData.Droid? = null
      var human: TestQuery.Data.QueryData.Human? = null
      while(true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> __typename = stringAdapter.fromResponse(reader)
          1 -> hero = nullableHeroAdapter.fromResponse(reader)
          2 -> droid = nullableDroidAdapter.fromResponse(reader)
          3 -> human = nullableHumanAdapter.fromResponse(reader)
          else -> break
        }
      }
      return TestQuery.Data.QueryData(
        __typename = __typename!!,
        hero = hero,
        droid = droid,
        human = human
      )
    }

    fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData) {
      writer.beginObject()
      writer.name("__typename")
      stringAdapter.toResponse(writer, value.__typename)
      writer.name("hero")
      nullableHeroAdapter.toResponse(writer, value.hero)
      writer.name("droid")
      nullableDroidAdapter.toResponse(writer, value.droid)
      writer.name("human")
      nullableHumanAdapter.toResponse(writer, value.human)
      writer.endObject()
    }

    companion object {
      val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.Typename,
        ResponseField(
          type = ResponseField.Type.Named.Object("Character"),
          fieldName = "hero",
          fieldSets = listOf(
            ResponseField.FieldSet("Droid", Hero.CharacterHero.RESPONSE_FIELDS),
            ResponseField.FieldSet("Human", Hero.CharacterHero.RESPONSE_FIELDS),
            ResponseField.FieldSet(null, Hero.OtherHero.RESPONSE_FIELDS),
          ),
        ),
        ResponseField(
          type = ResponseField.Type.Named.Object("Droid"),
          fieldName = "droid",
          arguments = mapOf<String, Any?>(
            "id" to 1),
          fieldSets = listOf(
            ResponseField.FieldSet("Droid", Droid.DroidDroid.RESPONSE_FIELDS),
            ResponseField.FieldSet(null, Droid.OtherDroid.RESPONSE_FIELDS),
          ),
        ),
        ResponseField(
          type = ResponseField.Type.Named.Object("Human"),
          fieldName = "human",
          arguments = mapOf<String, Any?>(
            "id" to 1),
          fieldSets = listOf(
            ResponseField.FieldSet("Human", Human.HumanHuman.RESPONSE_FIELDS),
            ResponseField.FieldSet(null, Human.OtherHuman.RESPONSE_FIELDS),
          ),
        )
      )

      val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
    }

    class Hero(
      responseAdapterCache: ResponseAdapterCache
    ) : ResponseAdapter<TestQuery.Data.QueryData.Hero> {
      val CharacterHeroAdapter: CharacterHero =
          com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.QueryData.Hero.CharacterHero(responseAdapterCache)

      val OtherHeroAdapter: OtherHero =
          com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.QueryData.Hero.OtherHero(responseAdapterCache)

      override fun fromResponse(reader: JsonReader): TestQuery.Data.QueryData.Hero {
        reader.beginObject()
        check(reader.nextName() == "__typename")
        val typename = reader.nextString()

        return when(typename) {
          "Droid" -> CharacterHeroAdapter.fromResponse(reader, typename)
          "Human" -> CharacterHeroAdapter.fromResponse(reader, typename)
          else -> OtherHeroAdapter.fromResponse(reader, typename)
        }
        .also { reader.endObject() }
      }

      override fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Hero) {
        when(value) {
          is TestQuery.Data.QueryData.Hero.CharacterHero -> CharacterHeroAdapter.toResponse(writer, value)
          is TestQuery.Data.QueryData.Hero.OtherHero -> OtherHeroAdapter.toResponse(writer, value)
        }
      }

      class CharacterHero(
        responseAdapterCache: ResponseAdapterCache
      ) {
        private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

        fun fromResponse(reader: JsonReader, __typename: String?):
            TestQuery.Data.QueryData.Hero.CharacterHero {
          var __typename: String? = __typename
          var name: String? = null
          while(true) {
            when (reader.selectName(RESPONSE_NAMES)) {
              0 -> __typename = stringAdapter.fromResponse(reader)
              1 -> name = stringAdapter.fromResponse(reader)
              else -> break
            }
          }
          return TestQuery.Data.QueryData.Hero.CharacterHero(
            __typename = __typename!!,
            name = name!!
          )
        }

        fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Hero.CharacterHero) {
          writer.beginObject()
          writer.name("__typename")
          stringAdapter.toResponse(writer, value.__typename)
          writer.name("name")
          stringAdapter.toResponse(writer, value.name)
          writer.endObject()
        }

        companion object {
          val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
            ResponseField.Typename,
            ResponseField(
              type = ResponseField.Type.NotNull(ResponseField.Type.Named.Other("String")),
              fieldName = "name",
            )
          )

          val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
        }
      }

      class OtherHero(
        responseAdapterCache: ResponseAdapterCache
      ) {
        private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

        fun fromResponse(reader: JsonReader, __typename: String?):
            TestQuery.Data.QueryData.Hero.OtherHero {
          var __typename: String? = __typename
          while(true) {
            when (reader.selectName(RESPONSE_NAMES)) {
              0 -> __typename = stringAdapter.fromResponse(reader)
              else -> break
            }
          }
          return TestQuery.Data.QueryData.Hero.OtherHero(
            __typename = __typename!!
          )
        }

        fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Hero.OtherHero) {
          writer.beginObject()
          writer.name("__typename")
          stringAdapter.toResponse(writer, value.__typename)
          writer.endObject()
        }

        companion object {
          val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
            ResponseField.Typename
          )

          val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
        }
      }
    }

    class Droid(
      responseAdapterCache: ResponseAdapterCache
    ) : ResponseAdapter<TestQuery.Data.QueryData.Droid> {
      val DroidDroidAdapter: DroidDroid =
          com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.QueryData.Droid.DroidDroid(responseAdapterCache)

      val OtherDroidAdapter: OtherDroid =
          com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.QueryData.Droid.OtherDroid(responseAdapterCache)

      override fun fromResponse(reader: JsonReader): TestQuery.Data.QueryData.Droid {
        reader.beginObject()
        check(reader.nextName() == "__typename")
        val typename = reader.nextString()

        return when(typename) {
          "Droid" -> DroidDroidAdapter.fromResponse(reader, typename)
          else -> OtherDroidAdapter.fromResponse(reader, typename)
        }
        .also { reader.endObject() }
      }

      override fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Droid) {
        when(value) {
          is TestQuery.Data.QueryData.Droid.DroidDroid -> DroidDroidAdapter.toResponse(writer, value)
          is TestQuery.Data.QueryData.Droid.OtherDroid -> OtherDroidAdapter.toResponse(writer, value)
        }
      }

      class DroidDroid(
        responseAdapterCache: ResponseAdapterCache
      ) {
        private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

        private val nullableStringAdapter: ResponseAdapter<String?> =
            NullableResponseAdapter(StringResponseAdapter)

        fun fromResponse(reader: JsonReader, __typename: String?):
            TestQuery.Data.QueryData.Droid.DroidDroid {
          var __typename: String? = __typename
          var name: String? = null
          var primaryFunction: String? = null
          while(true) {
            when (reader.selectName(RESPONSE_NAMES)) {
              0 -> __typename = stringAdapter.fromResponse(reader)
              1 -> name = stringAdapter.fromResponse(reader)
              2 -> primaryFunction = nullableStringAdapter.fromResponse(reader)
              else -> break
            }
          }
          return TestQuery.Data.QueryData.Droid.DroidDroid(
            __typename = __typename!!,
            name = name!!,
            primaryFunction = primaryFunction
          )
        }

        fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Droid.DroidDroid) {
          writer.beginObject()
          writer.name("__typename")
          stringAdapter.toResponse(writer, value.__typename)
          writer.name("name")
          stringAdapter.toResponse(writer, value.name)
          writer.name("primaryFunction")
          nullableStringAdapter.toResponse(writer, value.primaryFunction)
          writer.endObject()
        }

        companion object {
          val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
            ResponseField.Typename,
            ResponseField(
              type = ResponseField.Type.NotNull(ResponseField.Type.Named.Other("String")),
              fieldName = "name",
            ),
            ResponseField(
              type = ResponseField.Type.Named.Other("String"),
              fieldName = "primaryFunction",
            )
          )

          val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
        }
      }

      class OtherDroid(
        responseAdapterCache: ResponseAdapterCache
      ) {
        private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

        fun fromResponse(reader: JsonReader, __typename: String?):
            TestQuery.Data.QueryData.Droid.OtherDroid {
          var __typename: String? = __typename
          while(true) {
            when (reader.selectName(RESPONSE_NAMES)) {
              0 -> __typename = stringAdapter.fromResponse(reader)
              else -> break
            }
          }
          return TestQuery.Data.QueryData.Droid.OtherDroid(
            __typename = __typename!!
          )
        }

        fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Droid.OtherDroid) {
          writer.beginObject()
          writer.name("__typename")
          stringAdapter.toResponse(writer, value.__typename)
          writer.endObject()
        }

        companion object {
          val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
            ResponseField.Typename
          )

          val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
        }
      }
    }

    class Human(
      responseAdapterCache: ResponseAdapterCache
    ) : ResponseAdapter<TestQuery.Data.QueryData.Human> {
      val HumanHumanAdapter: HumanHuman =
          com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.QueryData.Human.HumanHuman(responseAdapterCache)

      val OtherHumanAdapter: OtherHuman =
          com.example.root_query_fragment_with_nested_fragments.adapter.TestQuery_ResponseAdapter.QueryData.Human.OtherHuman(responseAdapterCache)

      override fun fromResponse(reader: JsonReader): TestQuery.Data.QueryData.Human {
        reader.beginObject()
        check(reader.nextName() == "__typename")
        val typename = reader.nextString()

        return when(typename) {
          "Human" -> HumanHumanAdapter.fromResponse(reader, typename)
          else -> OtherHumanAdapter.fromResponse(reader, typename)
        }
        .also { reader.endObject() }
      }

      override fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Human) {
        when(value) {
          is TestQuery.Data.QueryData.Human.HumanHuman -> HumanHumanAdapter.toResponse(writer, value)
          is TestQuery.Data.QueryData.Human.OtherHuman -> OtherHumanAdapter.toResponse(writer, value)
        }
      }

      class HumanHuman(
        responseAdapterCache: ResponseAdapterCache
      ) {
        private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

        private val nullableStringAdapter: ResponseAdapter<String?> =
            NullableResponseAdapter(StringResponseAdapter)

        fun fromResponse(reader: JsonReader, __typename: String?):
            TestQuery.Data.QueryData.Human.HumanHuman {
          var __typename: String? = __typename
          var name: String? = null
          var homePlanet: String? = null
          while(true) {
            when (reader.selectName(RESPONSE_NAMES)) {
              0 -> __typename = stringAdapter.fromResponse(reader)
              1 -> name = stringAdapter.fromResponse(reader)
              2 -> homePlanet = nullableStringAdapter.fromResponse(reader)
              else -> break
            }
          }
          return TestQuery.Data.QueryData.Human.HumanHuman(
            __typename = __typename!!,
            name = name!!,
            homePlanet = homePlanet
          )
        }

        fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Human.HumanHuman) {
          writer.beginObject()
          writer.name("__typename")
          stringAdapter.toResponse(writer, value.__typename)
          writer.name("name")
          stringAdapter.toResponse(writer, value.name)
          writer.name("homePlanet")
          nullableStringAdapter.toResponse(writer, value.homePlanet)
          writer.endObject()
        }

        companion object {
          val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
            ResponseField.Typename,
            ResponseField(
              type = ResponseField.Type.NotNull(ResponseField.Type.Named.Other("String")),
              fieldName = "name",
            ),
            ResponseField(
              type = ResponseField.Type.Named.Other("String"),
              fieldName = "homePlanet",
            )
          )

          val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
        }
      }

      class OtherHuman(
        responseAdapterCache: ResponseAdapterCache
      ) {
        private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

        fun fromResponse(reader: JsonReader, __typename: String?):
            TestQuery.Data.QueryData.Human.OtherHuman {
          var __typename: String? = __typename
          while(true) {
            when (reader.selectName(RESPONSE_NAMES)) {
              0 -> __typename = stringAdapter.fromResponse(reader)
              else -> break
            }
          }
          return TestQuery.Data.QueryData.Human.OtherHuman(
            __typename = __typename!!
          )
        }

        fun toResponse(writer: JsonWriter, value: TestQuery.Data.QueryData.Human.OtherHuman) {
          writer.beginObject()
          writer.name("__typename")
          stringAdapter.toResponse(writer, value.__typename)
          writer.endObject()
        }

        companion object {
          val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
            ResponseField.Typename
          )

          val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
        }
      }
    }
  }

  class OtherData(
    responseAdapterCache: ResponseAdapterCache
  ) {
    private val stringAdapter: ResponseAdapter<String> = StringResponseAdapter

    fun fromResponse(reader: JsonReader, __typename: String?): TestQuery.Data.OtherData {
      var __typename: String? = __typename
      while(true) {
        when (reader.selectName(RESPONSE_NAMES)) {
          0 -> __typename = stringAdapter.fromResponse(reader)
          else -> break
        }
      }
      return TestQuery.Data.OtherData(
        __typename = __typename!!
      )
    }

    fun toResponse(writer: JsonWriter, value: TestQuery.Data.OtherData) {
      writer.beginObject()
      writer.name("__typename")
      stringAdapter.toResponse(writer, value.__typename)
      writer.endObject()
    }

    companion object {
      val RESPONSE_FIELDS: Array<ResponseField> = arrayOf(
        ResponseField.Typename
      )

      val RESPONSE_NAMES: List<String> = RESPONSE_FIELDS.map { it.responseName }
    }
  }
}