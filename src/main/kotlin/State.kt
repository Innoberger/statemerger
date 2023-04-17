/*
 * This is not a final implementation of the data structure. It must be updated to implement
 * a disjoint-set data structure (instead of using Kotlin List).
 */
data class State(val name: String, val cities: List<String>) {
    override fun equals(other: Any?) =
        other is State && other.name == this.name
}
