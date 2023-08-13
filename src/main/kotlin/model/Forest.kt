package model

internal class Forest {
    private var predecessor: HashMap<String, String> = HashMap()
    private var rank: HashMap<String, Int> = HashMap()

    internal fun makeSet(x: String) {
        predecessor[x] = x
        rank[x] = 0
    }

    /**
     * Merges the root nodes of `recessiveNode`
     * and `dominantNode` into one node, where
     * `dominantNode` will be the new root node.
     * Thus, the key/name of `dominantNode`'s
     * root node will be kept.
     *
     * @param recessiveNode Node to merge, will omit its name.
     * @param dominantNode  Node to merge, will keep its name.
     */
    internal fun union(recessiveNode: String, dominantNode: String) {
        link(findSet(recessiveNode)!!, findSet(dominantNode)!!)
    }

    /**
     * Links `recessiveNode` and `dominantNode` into one node,
     * where `dominantNode` will be the new root node.
     * Thus, the key/name of `dominantNode` will be kept.
     *
     * @param recessiveNode Node to link, will omit its name.
     * @param dominantNode  Node to link, will keep its name.
     */
    private fun link(recessiveNode: String, dominantNode: String) {
        if (rank[recessiveNode]!! > rank[dominantNode]!!) {
            predecessor[dominantNode] = recessiveNode
            return
        }

        predecessor[recessiveNode] = dominantNode

        if (rank[recessiveNode] == rank[dominantNode])
            rank[dominantNode] = rank[dominantNode]!!.plus(1)
    }

    internal fun findSet(x: String): String? {
        if (x != predecessor[x])
            predecessor[x] = findSet(predecessor[x]!!)!!

        return predecessor[x]
    }

    override fun toString(): String {
        return "Forest(predecessor=$predecessor, rank=$rank)"
    }
}