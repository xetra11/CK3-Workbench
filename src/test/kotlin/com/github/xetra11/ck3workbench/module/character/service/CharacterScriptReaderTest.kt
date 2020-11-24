package com.github.xetra11.ck3workbench.module.character.service

import com.github.xetra11.ck3workbench.module.character.Character
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CharacterScriptReaderTest {
    private val characterScriptReader: CharacterScriptReader = CharacterScriptReader()

    @Test
    fun `should parse txt file and convert it into character object`() {
        val actual: Character? = characterScriptReader.readCharacterScript()

        assertThat(actual!!).isNotNull
        assertThat(actual.name).isEqualTo("Thorak")
    }

    fun `should log error if file does not exist`() {

    }
    fun `should parse txt file and convert multiple entries into list of character objects`() {}
    fun `should validate character txt script file`() {}
}
