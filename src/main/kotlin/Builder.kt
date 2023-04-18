class Builder {
    companion object {
        fun buildGermany(): Node {
            val bw = Node.makeNode("Baden-Württemberg", "Stuttgart")
            val by = Node.makeNode("Bayern", "München")
            // to be continued (alphabetical order) ...

            return bw.union("Stuttgart", "München")
        }
    }
}