class Operations {
    /*
     * This is not a final implementation of the operations. The State (data) class must be updated to implement
     * a disjoint-set data structure (instead of using Kotlin List).
     */
    fun makeState(name: String, city: String): State {
        return State(name, listOf(city))
    }

    fun union(someState: State, anotherState: State): State {
        val mergedCities = ArrayList<String>();

        mergedCities.addAll(someState.cities);
        mergedCities.addAll(anotherState.cities);

        return State(someState.name, mergedCities)
    }
}