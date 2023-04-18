import model.Node

class Builder {
    companion object {
        fun buildGermany(): Node {
            val bw = Node.makeNode("Baden-Württemberg")
            val by = Node.makeNode("Bayern")
            // to be continued (alphabetical order) ...

            return bw.union("Stuttgart", "München")
        }
    }
}