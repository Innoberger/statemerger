
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
            /*
             * If city name and state name are equal (e.g. most city states like Berlin),
             * just add a corresponding suffix.
             */
            if (city == state.name)
                state.name = "${state.name} (Land)"

            when(i) {
                0 -> states.makeState(city, state.name)
                else -> states.makeState(city, UUID.randomUUID().toString())
            }
        }

        /*
         * Second, merge (dummy) states to that every city in a state points to it's state.
         */
        state.cities.forEachIndexed { i, city ->
            // TODO
        }
    }

    println(states.states)

    printSearchString(states, "Berlin")
    printSearchString(states, "Ingolstadt")
    printSearchString(states, "Stuttgart")
    printSearchString(states, "Potsdam")
}

fun printSearchString(states: States, city: String) {
    println("$city -> (...) -> ${states.find(city)}")
}