package config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

internal class ConfigParserTest {
    @Test
    fun `test buildStates constructs correct state-city relationships`() {
        val configFileContent = """
            states:
              - name: StateA
                cities:
                  - CityA1
                  - CityA2
              - name: StateB
                cities:
                  - CityB1
                  - CityB2
        """.trimIndent()

        val tempFile = File.createTempFile("testConfigFile", ".yaml")
        tempFile.writeText(configFileContent)

        val configParser = ConfigParser(tempFile)
        val states = configParser.buildStates()

        // Verify the state-city relationships
        assertEquals("StateA", states.findState("CityA1"))
        assertEquals("StateA", states.findState("CityA2"))
        assertEquals("StateB", states.findState("CityB1"))
        assertEquals("StateB", states.findState("CityB2"))

        tempFile.delete()
    }

    @Test
    fun `test buildStates with city name equals another state's name`() {
        val configFileContent = """
            states:
              - name: StateA
                cities:
                  - Hello
              - name: Hello
                cities:
                  - CityB
        """.trimIndent()

        val tempFile = File.createTempFile("testConfigFile", ".yaml")
        tempFile.writeText(configFileContent)

        val configParser = ConfigParser(tempFile)
        val states = configParser.buildStates()

        // when a city got the name of another state it will just point to itself
        // this is usually a risky choice! (unhappy flow)
        assertEquals("Hello", states.findState("Hello"))

        tempFile.delete()
    }

    @Test
    fun `test buildStates with on different states contain equal city`() {
        val configFileContent = """
            states:
              - name: StateA
                cities:
                  - SameCity
              - name: StateB
                cities:
                  - SameCity
        """.trimIndent()

        val tempFile = File.createTempFile("testConfigFile", ".yaml")
        tempFile.writeText(configFileContent)

        val configParser = ConfigParser(tempFile)
        val states = configParser.buildStates()

        // when different states got the same city, the last in config list is taken
        // this is usually a risky choice! (unhappy flow)
        assertEquals("StateB", states.findState("SameCity"))

        tempFile.delete()
    }
}
