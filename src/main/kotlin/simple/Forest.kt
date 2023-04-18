package simple

class Forest {
    var predecessor: HashMap<String, String> = HashMap()
    var rank: HashMap<String, Int> = HashMap()

    internal fun makeSet(x: String) {
        predecessor[x] = x
        rank[x] = 0
    }

    internal fun union(x: String, y: String) {
        link(findSet(x), findSet(y))
    }

    private fun link(x: String, y: String) {
        if (rank[x]!! > rank[y]!!) {
            predecessor[y] = x
            return
        }

        predecessor[x] = y

        if (rank[x] == rank[y])
            rank[y] = rank[y]!!.plus(1)
    }

    internal fun findSet(x: String): String {
        if (x != predecessor[x])
            predecessor[x] = findSet(predecessor[x]!!)

        return predecessor[x]!!
    }

    override fun toString(): String {
        return "Forest(predecessor=$predecessor, rank=$rank)"
    }
}