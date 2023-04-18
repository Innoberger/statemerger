package simple

class States {
    var states = Forest()

    fun makeState(city: String, state: String) {
        states.makeSet(city)
        states.makeSet(state)
        states.union(city, state)
    }

    fun union(stateA: String, stateB: String) {
        states.union(stateA, stateB)
    }

    fun find(city: String): String {
        return states.findSet(city)
    }
}