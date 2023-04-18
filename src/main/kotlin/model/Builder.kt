package model

import model.*

class Builder {
    companion object {
        fun buildGermany(): Node {
            val bw = StateWrapper.makeState("Stuttgart", "Baden-Württemberg")
            val by = StateWrapper.makeState("München", "Bayern")
            val be = StateWrapper.makeState("Berlin", "Berlin")
            // to be continued (alphabetical order) ...

            StateWrapper.union(bw, by)
            StateWrapper.union(bw, be)

            return bw
        }
    }
}