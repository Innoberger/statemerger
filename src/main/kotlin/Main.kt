
import com.charleskorn.kaml.Yaml
import config.Config
import kotlinx.serialization.decodeFromString
import simple.States
import java.io.File
import java.util.*

fun main() {
    val states = States()

    val configFile = File("src/main/resources/de.yaml")

    val config = configFile.bufferedReader().use {
        Yaml.default.decodeFromString<Config>(it.readText())
    }

    config.states.forEach { state ->
        /*
         * First, a dummy state for each city is created.
         * Only the first in the list will get a pointer to it's 'real' state.
         */
        state.cities.forEachIndexed { i, city ->
            var stateName = state.name
            var cityName = city

            /*
             * If city name and state name are equal (e.g. most city states like Berlin),
             * just add a corresponding suffix.
             */
            if (stateName == cityName) {
                stateName = "$stateName (Land)"
                cityName = "$cityName (Stadt)"
            }

            when(i) {
                0 -> states.makeState(cityName, stateName)
                else -> states.makeState(cityName, UUID.randomUUID().toString())
            }
        }

        /*
         * Second, merge (dummy) states to that every city in a state points to it's state.
         */
        //while(state.cities.any { states.find(it) != state.name }) {
            // TODO
        //}
    }

    println(states.states)

    printSearchString(states, "Berlin (Stadt)")
    printSearchString(states, "Ingolstadt")
    printSearchString(states, "Stuttgart")
    printSearchString(states, "Potsdam")
}

fun printSearchString(states: States, city: String) {
    println("$city -> (...) -> ${states.find(city)}")
}