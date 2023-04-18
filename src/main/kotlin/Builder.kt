import model.*

class Builder {
    companion object {
        fun buildGermany(): Node {
            val bw = StateWrapper.makeState("Baden-Württemberg", "Stuttgart")
            val by = StateWrapper.makeState("Bayern", "München")
            // to be continued (alphabetical order) ...

            return StateWrapper.union(bw, by)
        }
    }
}