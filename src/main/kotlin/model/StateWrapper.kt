package model

class StateWrapper {
    companion object {
        fun makeState(name: String, city: String): Node {
            val stateNode = Node.makeNode(name)
            val cityNode = Node.makeNode(city)

            return Node.union(stateNode, cityNode)
        }

        fun union(stateA: Node, stateB: Node): Node {
            return Node.union(stateA, stateB)
        }
    }
}