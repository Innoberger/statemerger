import model.*

class Builder {
    companion object {
        fun buildGermany(): Node {
            val bw = StateWrapper.makeState("Stuttgart", "Baden-Württemberg")
            val by = StateWrapper.makeState("München", "Bayern")
            // to be continued (alphabetical order) ...

            return StateWrapper.union(bw, by)
        }
    }
}