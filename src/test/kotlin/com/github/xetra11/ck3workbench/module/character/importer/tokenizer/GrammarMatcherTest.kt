package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType.*
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GrammarMatcherTest {
    private val grammarMatcher: GrammarMatcher = GrammarMatcher()

    @Test
    fun `should return the a word if the given test script is having only a matching OBJECT_ID`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID)
        )
        val actual: String = grammarMatcher
            .rule(grammar, TEST_1)
            .match

        assertThat(actual.trimWhiteSpace()).isEqualTo(TEST_1.trimWhiteSpace())
    }

    @Test
    fun `should return the a word if the given test script is having only a matching OBJECT_ID and ASSIGNMENT`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID, ASSIGNMENT)
        )
        val actual: String = grammarMatcher
            .rule(grammar, TEST_2)
            .match

        assertThat(actual.trimWhiteSpace()).isEqualTo(TEST_2.trimWhiteSpace())
    }

    @Test
    fun `should return the a word if the given test script is  matching OBJECT_ID, ASSIGNMENT, BLOCK_START`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID, ASSIGNMENT, BLOCK_START)
        )
        val actual: String = grammarMatcher
            .rule(grammar, TEST_3)
            .match

        assertThat(actual.trimWhiteSpace()).isEqualTo(TEST_3.trimWhiteSpace())
    }

    @Test
    fun `should return an error if given script tokens won't match wit grammar`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID, ASSIGNMENT, BLOCK_START)
        )
        val actual = grammarMatcher
            .rule(grammar, INVALID_1)

        assertThat(actual.hasError).isTrue
        assertThat(actual.errorReason).isEqualTo("Token order invalid")
        assertThat(actual.match).isEqualTo("")
    }

    private fun String.trimWhiteSpace(): String {
        return this.filterNot { it.isWhitespace() }
    }

    companion object {
        const val TEST_1 = """
            thorak
        """
        const val TEST_2 = """
            thorak =
        """
        const val TEST_3 = """
            thorak = {
        """
        const val INVALID_1 = """
            thorak {
        """
        const val SCRIPT_EXAMPLE_1 = """
            thorak =  {
                name = "Thorak"
            }
        """
    }

}
