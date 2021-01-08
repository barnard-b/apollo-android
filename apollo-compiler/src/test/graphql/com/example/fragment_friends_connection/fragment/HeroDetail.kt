// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.fragment_friends_connection.fragment

import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter", "PropertyName",
    "RemoveRedundantQualifierName")
interface HeroDetail {
  val __typename: String

  /**
   * The name of the character
   */
  val name: String

  /**
   * The friends of the character exposed as a connection with edges
   */
  val friendsConnection: FriendsConnection

  /**
   * A connection object for a character's friends
   */
  interface FriendsConnection {
    /**
     * The total number of friends
     */
    val totalCount: Int?

    /**
     * The edges for each of the character's friends.
     */
    val edges: List<Edge?>?

    /**
     * An edge object for a character's friends
     */
    interface Edge {
      /**
       * The character represented by this friendship edge
       */
      val node: Node?

      /**
       * A character from the Star Wars universe
       */
      interface Node {
        /**
         * The name of the character
         */
        val name: String
      }
    }
  }

  companion object {
    val FRAGMENT_DEFINITION: String = """
        |fragment HeroDetails on Character {
        |  __typename
        |  name
        |  friendsConnection {
        |    totalCount
        |    edges {
        |      node {
        |        name
        |      }
        |    }
        |  }
        |}
        """.trimMargin()
  }
}
