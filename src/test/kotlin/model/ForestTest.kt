package model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ForestTest {
    private lateinit var forest: Forest

    @BeforeEach
    fun setUp() {
        forest = Forest()
    }

    @Test
    fun `test makeSet creates a new set`() {
        forest.makeSet("someElement")

        assertEquals(forest.predecessor.size, 1)
        assertEquals(forest.predecessor["someElement"], "someElement")

        assertEquals(forest.rank.size, 1)
        assertEquals(forest.rank["someElement"], 0)
    }

    @Test
    fun `test union merges two sets and updates root node`() {
        forest.makeSet("someElement")
        forest.makeSet("anotherElement")

        forest.union("someElement", "anotherElement")

        assertEquals(forest.predecessor["someElement"], "anotherElement")
        assertEquals(forest.predecessor["anotherElement"], "anotherElement")
    }

    @Test
    fun `test union merges two sets and updates ranks`() {
        forest.makeSet("someElement")
        forest.makeSet("anotherElement")

        forest.union("someElement", "anotherElement")

        assertEquals(forest.rank["someElement"], 0)
        assertEquals(forest.rank["anotherElement"], 1)
    }

    @Test
    fun `test union by rank`() {
        forest.makeSet("1stElement")
        forest.makeSet("2ndElement")
        forest.makeSet("3rdElement")

        forest.union("2ndElement", "1stElement")

        /**
         * Instead of using 1stElement as the new root node,
         * because it higher ranks will always win over dominantNode.
         */
        forest.union("1stElement", "3rdElement")

        assertEquals(forest.predecessor["1stElement"], "1stElement")
        assertEquals(forest.predecessor["2ndElement"], "1stElement")
        assertEquals(forest.predecessor["3rdElement"], "1stElement")
    }

    @Test
    fun `test findSet with path compression`() {
        forest.makeSet("1stElement")
        forest.makeSet("2ndElement")
        forest.makeSet("3rdElement")

        forest.union("2ndElement", "1stElement")

        assertEquals(forest.predecessor["3rdElement"], "3rdElement")

        forest.union("3rdElement", "2ndElement")

        assertEquals(forest.predecessor["3rdElement"], "1stElement")
        assertEquals(forest.findSet("3rdElement"), "1stElement")
    }

    @Test
    fun `test toString provides correct representation`() {
        forest.makeSet("someElement")
        forest.makeSet("anotherElement")

        forest.union("someElement", "anotherElement")
        val expected = "Forest(predecessor={someElement=anotherElement, anotherElement=anotherElement}, rank={someElement=0, anotherElement=1})"
        assertEquals(expected, forest.toString())
    }
}
