//import model.Builder
import simple.*
fun main() {
    //println(Builder.buildGermany().toString())

    val states = States()

    states.makeState("Stuttgart", "Baden-Württemberg")
    states.makeState("München", "Bayern")
    states.makeState("Berlin", "Berlin")

    states.union("Baden-Württemberg", "Bayern")
    states.union("Bayern", "Berlin")

    println(states.states)

    println(states.find("München"))
}