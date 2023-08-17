package model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class StatesTest {
    private lateinit var states: States

    @BeforeEach
    fun setUp() {
        states = States()
    }

    @Test
    fun `test makeState creates a new forest with two nodes`() {
        states.makeState("someCity", "someState")

        assertEquals(2, states.states.predecessor.size)
        assertEquals("someState", states.states.predecessor["someCity"])
        assertEquals("someState", states.states.predecessor["someState"])

        assertEquals(2, states.states.rank.size)
        assertEquals(0, states.states.rank["someCity"])
        assertEquals(1, states.states.rank["someState"])
    }

    @Test
    fun `test union merges two states and updates root node`() {
        states.makeState("cityA", "stateA")
        states.makeState("cityB", "stateB")

        states.union("stateB", "stateA")

        assertEquals("stateA", states.findState("cityA"))
        assertEquals("stateA", states.findState("cityB"))
    }

    @Test
    fun `test union merges two states and updates ranks`() {
        states.makeState("cityA", "stateA")
        states.makeState("cityB", "stateB")

        states.union("stateB", "stateA")

        assertEquals(0, states.states.rank["cityA"])
        assertEquals(0, states.states.rank["cityB"])
        assertEquals(2, states.states.rank["stateA"])
        assertEquals(1, states.states.rank["stateB"])
    }

    @Test
    fun `test union by rank`() {
        states.makeState("cityA", "stateA")
        states.makeState("cityB", "stateB")
        states.makeState("cityC", "stateC")

        states.union("stateB", "stateA")

        /**
         * Instead of using stateC as the new root node,
         * it chooses stateA as the new root node,
         * because higher ranks will always win over dominantState.
         */
        states.union("stateA", "stateC")

        assertEquals("stateA", states.states.predecessor["stateA"])
        assertEquals("stateA", states.states.predecessor["stateB"])
        assertEquals("stateA", states.states.predecessor["stateC"])
    }

    @Test
    fun `test union is always merging root nodes`() {
        states.makeState("someCity", "someState")
        states.makeState("anotherCity", "anotherState")

        assertEquals("someState", states.states.predecessor["someCity"])
        assertEquals("someState", states.states.predecessor["someState"])
        assertEquals("anotherState", states.states.predecessor["anotherCity"])
        assertEquals("anotherState", states.states.predecessor["anotherState"])

        states.union("someCity", "anotherCity")

        assertEquals("someState", states.states.predecessor["someCity"])
        assertEquals("anotherState", states.states.predecessor["someState"])
        assertEquals("anotherState", states.states.predecessor["anotherCity"])
        assertEquals("anotherState", states.states.predecessor["anotherState"])
    }

    @Test
    fun `test findState with path compression`() {
        states.makeState("cityA", "stateA")
        states.makeState("cityB", "stateB")
        states.makeState("cityC", "stateC")

        states.union("stateB", "stateA")
        states.union("stateC", "stateB")

        assertEquals("stateC", states.states.predecessor["cityC"])
        assertEquals("stateA", states.findState("cityC"))
        assertEquals("stateA", states.states.predecessor["cityC"])
    }

    @Test
    fun `test findState fails for unknown city`() {
        assertThrows<NullPointerException> {
            states.findState("nonExistingCity")
        }
    }
}
