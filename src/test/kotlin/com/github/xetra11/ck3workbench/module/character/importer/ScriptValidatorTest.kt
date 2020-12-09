package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.MatcherResult
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.ASSIGNMENT
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.BLOCK_START
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.OBJECT_ID
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar
import com.nhaarman.mockitokotlin2.given
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
        val grammarDefinitionFile = File("grammars/character_grammar.grm")
        val expectedGrammar = Grammar("Character", listOf(OBJECT_ID, ASSIGNMENT, BLOCK_START))
        val expectedResult = MatcherResult("", hasError = false, "")

        given(mockedGrammarParser.process(grammarDefinitionFile.readText())).willReturn(expectedGrammar)
        given(mockedGrammarMatcher.rule(expectedGrammar, characterScript.readLines())).willReturn(expectedResult)

        val actual: Boolean = scriptValidator.validate(characterScript, "Character")

        assertThat(actual).isTrue
    }

    @Test
    fun `should return character script as invalid if given type is not matching a grammar definition name`() {
        val characterScript = File("src/test/resources/fixtures/character/test_character_simple.txt")
        val grammarDefinitionFile = File("grammars/character_grammar.grm")
        val expectedGrammar = Grammar("Character", listOf(OBJECT_ID, ASSIGNMENT, BLOCK_START))
        val expectedResult = MatcherResult("", hasError = false, "")

        given(mockedGrammarParser.process(grammarDefinitionFile.readText())).willReturn(expectedGrammar)
        given(mockedGrammarMatcher.rule(expectedGrammar, characterScript.readLines())).willReturn(expectedResult)

        val actual: Boolean = scriptValidator.validate(characterScript, "WrongType")

        assertThat(actual).isFalse
    }
}
