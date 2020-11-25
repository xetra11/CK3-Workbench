package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.Character
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class CharacterScriptReaderTest {
    private val characterScriptReader: CharacterScriptReader = CharacterScriptReader()

    @Test
    fun `should parse txt file and convert it into character object`() {
        val file = File("src/test/resources/fixtures/character/test_character.txt")
        val actual: Character? = characterScriptReader.readCharacterScript(file)

        assertThat(actual!!).isNotNull
        assertThat(actual.name).isEqualTo("Thorak")
    }

    @Test
    fun `should log error if file does not exist`() {
        val file = File("does/not/exist.txt")

        val actual: Character? = characterScriptReader.readCharacterScript(file)

        assertThat(actual).isNull()
    }
    fun `should parse txt file and convert multiple entries into list of character objects`() {}
    fun `should validate character txt script file`() {}
}
