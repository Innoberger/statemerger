package model;

class State private constructor (
    var name: Node,
    var city: Node
) {
    companion object {
        fun makeState(name: String, city: String): State {
            val stateNode = Node(null, 0, name)
            val cityNode = Node(stateNode, 1, city)

            stateNode.predecessor = stateNode
            return State(stateNode, cityNode);
        }
    }
}