import model.*

class Builder {
    companion object {
        fun buildGermany(): State {
            val bw = State.makeState("Baden-Württemberg", "Stuttgart")
            val by = State.makeState("Bayern", "München")
            // to be continued (alphabetical order) ...

            return bw
        }
    }
}