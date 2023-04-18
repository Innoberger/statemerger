class Builder {
    companion object {
        fun buildGermany() {
            val bw = SetOperations.makeSet("Baden-Württemberg -> Stuttgart")
            val by = SetOperations.makeSet("Bayern -> München")
            // to be continued (alphabetical order) ...

            SetOperations.union(bw, by)
        }
    }
}