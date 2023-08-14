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

        assertEquals(states.states.predecessor.size, 2)
        assertEquals(states.states.predecessor["someCity"], "someState")
        assertEquals(states.states.predecessor["someState"], "someState")

        assertEquals(states.states.rank.size, 2)
        assertEquals(states.states.rank["someCity"], 0)
        assertEquals(states.states.rank["someState"], 1)
    }

    @Test
    fun `test union merges two sets and updates root node`() {
        states.makeState("cityA", "stateA")
        states.makeState("cityB", "stateB")

        states.union("stateB", "stateA")

        assertEquals(states.findState("cityA"), "stateA")
        assertEquals(states.findState("cityB"), "stateA")
    }

    @Test
    fun `test union merges two sets and updates ranks`() {
        states.makeState("cityA", "stateA")
        states.makeState("cityB", "stateB")

        states.union("stateB", "stateA")

        assertEquals(states.states.rank["cityA"], 0)
        assertEquals(states.states.rank["cityB"], 0)
        assertEquals(states.states.rank["stateA"], 2)
        assertEquals(states.states.rank["stateB"], 1)
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

        assertEquals(states.states.predecessor["stateA"], "stateA")
        assertEquals(states.states.predecessor["stateB"], "stateA")
        assertEquals(states.states.predecessor["stateC"], "stateA")
    }

    @Test
    fun `test union is always merging root nodes`() {
        states.makeState("someCity", "someState")
        states.makeState("anotherCity", "anotherState")

        assertEquals(states.states.predecessor["someCity"], "someState")
        assertEquals(states.states.predecessor["someState"], "someState")
        assertEquals(states.states.predecessor["anotherCity"], "anotherState")
        assertEquals(states.states.predecessor["anotherState"], "anotherState")

        states.union("someCity", "anotherCity")

        assertEquals(states.states.predecessor["someCity"], "someState")
        assertEquals(states.states.predecessor["someState"], "anotherState")
        assertEquals(states.states.predecessor["anotherCity"], "anotherState")
        assertEquals(states.states.predecessor["anotherState"], "anotherState")
    }

    @Test
    fun `test findState with path compression`() {
        states.makeState("cityA", "stateA")
        states.makeState("cityB", "stateB")
        states.makeState("cityC", "stateC")

        states.union("stateB", "stateA")
        states.union("stateC", "stateB")

        assertEquals(states.states.predecessor["cityC"], "stateC")
        assertEquals(states.findState("cityC"), "stateA")
        assertEquals(states.states.predecessor["cityC"], "stateA")
    }

    @Test
    fun `test findState fails for unknown city`() {
        assertThrows<NullPointerException> {
            states.findState("nonExistingCity")
        }
    }
}
