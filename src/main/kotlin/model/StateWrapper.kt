package model

class StateWrapper {
    companion object {
        fun makeState(city: String, name: String): Node {
            val cityNode = Node.makeNode(city)
            val stateNode = Node.makeNode(name)

            return Node.union(cityNode, stateNode)
        }

        fun union(stateA: Node, stateB: Node): Node {
            return Node.union(stateA, stateB)
        }
    }
}