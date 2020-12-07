package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class GrammarIntegrationTest {
    private val grammarParser: GrammarParser = GrammarParser()
    private val grammarMatcher: GrammarMatcher = GrammarMatcher()

    @Test
    fun `should parse grammar file and match given character script file`() {
        val grammarFile = File("src/test/resources/test_grammar_file.grm")
        val scriptFile = File("src/test/resources/fixtures/character/test_character_simple.txt")

        val grammarDefinitionContent = grammarFile.readText()
        val parsedGrammar = grammarParser.process(grammarDefinitionContent)

        val actual = grammarMatcher.rule(parsedGrammar, scriptFile.readLines())

        assertThat(actual.hasError).isFalse
        assertThat(actual.errorReason).isEqualTo("")
    }

    @Test
    fun `should return error if the given file is not matching the definition due to missing attribute`() {
        val grammarFile = File("src/test/resources/test_grammar_file.grm")
        val scriptFile = File("src/test/resources/fixtures/character/test_character_simple_error.txt")

        val grammarDefinitionContent = grammarFile.readText()
        val parsedGrammar = grammarParser.process(grammarDefinitionContent)

        val actual = grammarMatcher.rule(parsedGrammar, scriptFile.readLines())

        assertThat(actual.hasError).isTrue
        assertThat(actual.errorReason).isEqualTo("Token order invalid")
    }
}
