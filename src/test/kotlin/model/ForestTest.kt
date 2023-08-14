package model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class ForestTest {
    private lateinit var forest: Forest

    @BeforeEach
    fun setUp() {
        forest = Forest()
    }

    @Test
    fun `test makeSet creates a new set`() {
        forest.makeSet("someElement")

        assertEquals(1, forest.predecessor.size)
        assertEquals("someElement", forest.predecessor["someElement"])

        assertEquals(1, forest.rank.size)
        assertEquals(0, forest.rank["someElement"])
    }

    @Test
    fun `test union merges two sets and updates root node`() {
        forest.makeSet("someElement")
        forest.makeSet("anotherElement")

        forest.union("someElement", "anotherElement")

        assertEquals("anotherElement", forest.predecessor["someElement"])
        assertEquals("anotherElement", forest.predecessor["anotherElement"])
    }

    @Test
    fun `test union merges two sets and updates ranks`() {
        forest.makeSet("someElement")
        forest.makeSet("anotherElement")

        forest.union("someElement", "anotherElement")

        assertEquals(0, forest.rank["someElement"])
        assertEquals(1, forest.rank["anotherElement"])
    }

    @Test
    fun `test union by rank`() {
        forest.makeSet("1stElement")
        forest.makeSet("2ndElement")
        forest.makeSet("3rdElement")

        forest.union("2ndElement", "1stElement")

        /**
         * Instead of using 3rdElement as the new root node,
         * it uses 1stElement as the new root node,
         * because higher ranks will always win over dominantNode.
         */
        forest.union("1stElement", "3rdElement")

        assertEquals("1stElement", forest.predecessor["1stElement"])
        assertEquals("1stElement", forest.predecessor["2ndElement"])
        assertEquals("1stElement", forest.predecessor["3rdElement"])
    }

    @Test
    fun `test union is always merging root nodes`() {
        forest.makeSet("1stElement")
        forest.makeSet("2ndElement")
        forest.makeSet("3rdElement")
        forest.makeSet("4thElement")

        assertEquals("1stElement", forest.predecessor["1stElement"])
        assertEquals("2ndElement", forest.predecessor["2ndElement"])
        assertEquals("3rdElement", forest.predecessor["3rdElement"])
        assertEquals("4thElement", forest.predecessor["4thElement"])

        forest.union("2ndElement", "1stElement")
        forest.union("4thElement", "3rdElement")

        assertEquals("1stElement", forest.predecessor["1stElement"])
        assertEquals("1stElement", forest.predecessor["2ndElement"])
        assertEquals("3rdElement", forest.predecessor["3rdElement"])
        assertEquals("3rdElement", forest.predecessor["4thElement"])

        forest.union("3rdElement", "1stElement")

        assertEquals("1stElement", forest.predecessor["1stElement"])
        assertEquals("1stElement", forest.predecessor["2ndElement"])
        assertEquals("1stElement", forest.predecessor["3rdElement"])
        assertEquals("3rdElement", forest.predecessor["4thElement"])
    }

    @Test
    fun `test findSet with path compression`() {
        forest.makeSet("1stElement")
        forest.makeSet("2ndElement")
        forest.makeSet("3rdElement")

        forest.union("2ndElement", "1stElement")

        assertEquals("3rdElement", forest.predecessor["3rdElement"])

        forest.union("3rdElement", "2ndElement")

        assertEquals("1stElement", forest.findSet("3rdElement"))
        assertEquals("1stElement", forest.predecessor["3rdElement"])
    }

    @Test
    fun `test findSet fails for unknown element`() {
        assertThrows<NullPointerException> {
            forest.findSet("nonExistingElement")
        }
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
