package model

/**
 * Node class for a disjoint-set forest.
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
class Node internal constructor (
    var predecessor: Node? = null,
    var rank: Int,
    var data: String,
) {
    override fun toString(): String {
        if (predecessor == this)
            return "Node(rank=$rank, data='$data')"

        return "Node(predecessor=${predecessor!!.data}, rank=$rank, data='$data')"
    }

    companion object {
        internal fun makeNode(data: String): Node {
            val node = Node(null, 0, data)
            node.predecessor = node
            return node
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

        private fun findNode(node: Node): Node {
            if (node != node.predecessor)
                node.predecessor = findNode(node.predecessor!!)

            return node.predecessor!!
        }

        internal fun union(nodeA: Node, nodeB: Node): Node {
            return link(findNode(nodeA), findNode(nodeB))
        }
    }
}