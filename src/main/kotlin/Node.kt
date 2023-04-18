/**
 * Node class for a disjoint-set forest.
 * Each node points to a `predecessor` node,
 * has a `rank` and can store `key` and `value` data.
 *
 * The root node does not contain a predecessor,
 * it must be set to itself.
 *
 * @param predecessor   Preceding tree node
 * @param rank          Upper bound on the node height
 * @param key           Optional stored key data
 * @param value         Optional stored value data
 */
class Node private constructor (
    var predecessor: Node? = null,
    var rank: Int,
    val key: String,
    val value: String
) {
    fun findNode(value: String): Node? {
        if (value != this.predecessor!!.value)
            this.predecessor = findNode(this.predecessor!!.value)

        return this.predecessor
    }

    fun union(valueA: String, valueB: String): Node {
        return link(findNode(valueA)!!, findNode(valueB)!!)
    }

    override fun toString(): String {
        if (predecessor == this)
            return "Node(predecessor={root}, rank=$rank, key='$key', value='$value')"

        return "Node(predecessor=$predecessor, rank=$rank, key='$key', value='$value')"
    }

    companion object {
        fun makeNode(key: String, value: String): Node {
            val node = Node(null, 0, key, value)
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