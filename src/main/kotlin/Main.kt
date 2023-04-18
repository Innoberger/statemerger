//import model.Builder
import simple.*

fun main() {
    //println(Builder.buildGermany().toString())

    val states = States()

    states.makeState("Stuttgart", "Baden-Württemberg")
    states.makeState("München", "Bayern")
    states.makeState("BerlinStadt", "BerlinLand")

    states.union("Baden-Württemberg", "Bayern")
    states.union("Bayern", "BerlinLand")

    println(states.states)

    println(states.find("München"))
}