class SetOperations {
    companion object {
        fun makeSet(data: Any?): Node {
            return Node(null, 0, data)
        }

        fun findSet(node: Node?): Node? {
            if (node != node!!.predecessor)
                node.predecessor = findSet(node.predecessor)

            return node.predecessor
        }

        private fun link(nodeA: Node?, nodeB: Node?) {
            if (nodeA!!.rank > nodeB!!.rank) {
                nodeB.predecessor = nodeA
                return
            }

            nodeA.predecessor = nodeB

            if (nodeA.rank == nodeB.rank)
                nodeB.rank++
        }

        fun union(nodeA: Node?, nodeB: Node?) {
            link(findSet(nodeA), findSet(nodeB))
        }
    }
}