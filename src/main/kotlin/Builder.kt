class Builder {
    fun buildGermany(): List<State> {
        val operations = Operations()

        val bw = operations.makeState("Baden-Württemberg", "Stuttgart")
        val by = operations.makeState("Bayern", "München")
        // to be continued (alphabetical order) ...

        return listOf(bw, by)
    }
}