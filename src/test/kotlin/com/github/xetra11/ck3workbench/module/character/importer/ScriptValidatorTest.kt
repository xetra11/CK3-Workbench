package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class ScriptValidatorTest {
    private val mockedGrammarMatcher: GrammarMatcher = mock()
    private val mockedGrammarParser: GrammarParser = mock()

    private val scriptValidator: ScriptValidator = ScriptValidator(mockedGrammarMatcher, mockedGrammarParser)

    @Test
    fun `should return character script as valid`() {
        val characterScript = File("src/test/resources/fixtures/character/test_character_simple.txt")

        val actual: Boolean = scriptValidator.validate(characterScript)

        assertThat(actual).isTrue
    }

    fun `should return character script as invalid`() {
    }
}
