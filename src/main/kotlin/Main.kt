
import config.ConfigParser
import model.States

fun main() {
    val germany = ConfigParser("src/main/resources/de.yaml").buildStates()

    // temporary print out the disjoint-set forest
    println(germany.states)

    // temporary hardcoded tests
    printSearchString(germany, "Berlin (Stadt)")
    printSearchString(germany, "Ingolstadt")
    printSearchString(germany, "Karlsruhe")
    printSearchString(germany, "Mannheim")
    printSearchString(germany, "Potsdam")
    printSearchString(germany, "Cottbus")
    printSearchString(germany, "Bremen (Stadt)")
    printSearchString(germany, "Bremerhaven")
}

fun printSearchString(states: States, city: String) {
    println("$city -> (...) -> ${states.find(city)}")
}