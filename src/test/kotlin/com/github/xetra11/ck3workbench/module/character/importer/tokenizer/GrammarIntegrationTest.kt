package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File

internal class GrammarIntegrationTest {
    private val grammarParser: GrammarParser = GrammarParser()
    private val grammarValidator: GrammarValidator = GrammarValidator()

    @Test
    fun `should parse grammar file and match given character script that contains sub object`() {
        val grammarFile = File("src/test/resources/test_grammar_subobject.grm")
        val scriptFile = File("src/test/resources/fixtures/character/test_character_subobject.txt")

        val grammarDefinitionContent = grammarFile.readText(Charsets.UTF_8)
        val parsedGrammar = grammarParser.process(grammarDefinitionContent)

        val actual = grammarValidator.rule(parsedGrammar, scriptFile.readLines())

        assertThat(actual.hasError).isFalse
        assertThat(actual.errorReason).isEqualTo("")
    }

    @Test
    fun `should parse grammar file and match given character script file`() {
        val grammarFile = File("src/test/resources/test_grammar_file.grm")
        val scriptFile = File("src/test/resources/fixtures/character/test_character_simple.txt")

        val grammarDefinitionContent = grammarFile.readText()
        val parsedGrammar = grammarParser.process(grammarDefinitionContent)

        val actual = grammarValidator.rule(parsedGrammar, scriptFile.readLines())

        assertThat(actual.hasError).isFalse
        assertThat(actual.errorReason).isEqualTo("")
    }

    @Disabled
    @Test
    fun `should return valid if the given file has optionals missing and tokens in different order`() {
        val grammarFile = File("src/test/resources/test_grammar_file_2.grm")
        val scriptFile = File("src/test/resources/fixtures/character/test_character_with_missing_subobject_attribute.txt")

        val grammarDefinitionContent = grammarFile.readText()
        val parsedGrammar = grammarParser.process(grammarDefinitionContent)

        val actual = grammarValidator.rule(parsedGrammar, scriptFile.readLines())

        assertThat(actual.hasError).isTrue
    }

    @Test
    fun `should return error if the given file is not matching the definition due to missing attribute`() {
        val grammarFile = File("src/test/resources/test_grammar_file.grm")
        val scriptFile = File("src/test/resources/fixtures/character/test_character_simple_error.txt")

        val grammarDefinitionContent = grammarFile.readText()
        val parsedGrammar = grammarParser.process(grammarDefinitionContent)

        val actual = grammarValidator.rule(parsedGrammar, scriptFile.readLines())

        assertThat(actual.hasError).isTrue
    }
}
