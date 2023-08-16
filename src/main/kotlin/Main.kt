
import config.ConfigParser
import model.States
import java.io.File

fun main() {
    val germany = ConfigParser(File("src/main/resources/de.yaml")).buildStates()

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
    println("$city -> (...) -> ${states.findState(city)}")
}