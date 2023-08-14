package config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import model.States
import java.io.File
import java.util.*

class ConfigParser(
    private val filePath: String
) {

    fun buildStates(): States {
        val states = States()

        val configFile = File(filePath)

        val config = configFile.bufferedReader().use {
            Yaml.default.decodeFromString<Config>(it.readText())
        }

        /*
         * We process each configured state.
         */
        config.states.forEach { state ->
            val stateName = state.name

            /*
             * For every state, we process every configured city.
             */
            state.cities.forEachIndexed { i, city ->
                when (i) {
                    /*
                     * The 1st city (index = 0) is defined as the capital city of a state.
                     * We say it will be assigned to the "real" state instead of a dummy city.
                     */
                    0 -> states.makeState(city, stateName)

                    else -> {
                        val dummyState = UUID.randomUUID().toString()

                        /*
                         * For every other element, a dummy state for each city is created.
                         * Only the first in the list will get a pointer to it's 'real' state.
                         *
                         * UUID is used to ensure that every dummy state is disjoint (unique).
                         */
                        states.makeState(city, dummyState)

                        /*
                         * Now we can merge the dummyState of cityName with the real state.
                         * Keep in mind that the second argument will set the node name.
                         * At this point it would not matter if we used dummyState as first argument
                         * or city, because union will always link the root node of the tree.
                         * But for better understanding, we use the dummyState.
                         */
                        states.union(dummyState, stateName)
                    }
                }
            }
        }

        return states
    }

}