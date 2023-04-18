class Builder {
    fun buildGermany() {
        val setOperations = SetOperations()

        val bw = setOperations.makeSet("Baden-Württemberg -> Stuttgart")
        val by = setOperations.makeSet("Bayern -> München")
        // to be continued (alphabetical order) ...

        setOperations.union(bw, by)
    }
}