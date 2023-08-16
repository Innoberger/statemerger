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
}
