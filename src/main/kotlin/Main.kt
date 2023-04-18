
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
        state.cities.forEach { city ->
            states.makeState(city, UUID.randomUUID().toString())
        }
    }

    println(states.states)

    println(states.find("München"))
}