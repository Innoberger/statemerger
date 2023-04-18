package model

/**
 * model.Node class for a disjoint-set forest.
 * Each node points to a `predecessor` node,
 * has a `rank` and can store `data`.
 *
 * The root node does not contain a predecessor,
 * it must be set to itself.
 *
 * @param predecessor   Preceding tree node
 * @param rank          Upper bound on the node height
 * @param data          Optionally stored data
 */
class Node private constructor (
    var predecessor: Node? = null,
    var rank: Int,
    val data: String,
) {
    fun findNode(data: String): Node? {
        if (data != this.predecessor!!.data)
            this.predecessor = findNode(this.predecessor!!.data)

        return this.predecessor
    }

    fun union(valueA: String, valueB: String): Node {
        return link(findNode(valueA)!!, findNode(valueB)!!)
    }

    override fun toString(): String {
        if (predecessor == this)
            return "model.Node(predecessor={root}, rank=$rank, data='$data')"

        return "model.Node(predecessor=${predecessor!!.data}, rank=$rank, data='$data')"
    }

    companion object {
        fun makeNode(data: String): Node {
            val node = Node(null, 0, data)
            node.predecessor = node
            return node;
        }

       private fun link(nodeA: Node, nodeB: Node): Node {
            if (nodeA.rank > nodeB.rank) {
                nodeB.predecessor = nodeA
                return nodeA
            }

            nodeA.predecessor = nodeB

            if (nodeA.rank == nodeB.rank)
                nodeB.rank++

            return nodeB
        }
    }
}