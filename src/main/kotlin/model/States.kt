package model

class States {
    var states = Forest()

    fun makeState(city: String, state: String) {
        states.makeSet(city)
        states.makeSet(state)
        states.union(city, state)
    }

    /**
     * Merges `recessiveState` and `dominantState` into one state,
     * where `dominantState` will be the root node.
     * Thus, the name of `dominantState` will be kept.
     *
     * @param recessiveState    State to merge, will omit its name.
     * @param dominantState     State to merge, will keep its name.
     */
    fun union(recessiveState: String, dominantState: String) {
        states.union(recessiveState, dominantState)
    }

    fun find(city: String): String? {
        return states.findSet(city)
    }
}