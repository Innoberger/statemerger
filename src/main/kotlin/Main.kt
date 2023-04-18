//import model.Builder
import simple.*

fun main() {
    //println(Builder.buildGermany().toString())

    val states = States()

    // first, we will create a state for each federal state of Germany only containing it's capital
    states.makeState("Stuttgart", "Baden-Württemberg")
    states.makeState("München", "Bayern")
    states.makeState("BerlinStadt", "BerlinLand")
    states.makeState("Potsdam", "Brandenburg")
    states.makeState("BremenStadt", "BremenLand")
    states.makeState("HamburgStadt", "HamburgLand")
    states.makeState("Wiesbaden", "Hessen")
    states.makeState("Schwerin", "Mecklenburg-Vorpommern")
    states.makeState("Hannover", "Niedersachsen")
    states.makeState("Düsseldorf", "Nordrhein-Westfalen")
    states.makeState("Mainz", "Rheinland-Pfalz")
    states.makeState("Saarbrücken", "Saarland")
    states.makeState("Dresden", "Sachsen")
    states.makeState("Magdeburg", "Sachsen-Anhalt")
    states.makeState("Kiel", "Schleswig-Holstein")
    states.makeState("Erfurt", "Thüringen")

    println(states.states)

    println(states.find("München"))
}