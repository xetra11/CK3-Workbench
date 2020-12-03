package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType.*
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GrammarMatcherTest {
    private val grammarMatcher: GrammarMatcher = GrammarMatcher()

    @Test
    fun `should return a match for given script string and grammar definition`() {
        val grammar = Grammar(
            "CHARACTER",
            listOf(
                OBJECT_ID,
                ASSIGNMENT,
                BLOCK_START,
                ATTRIBUTE_ID,
                ATTRIBUTE_VALUE,
                BLOCK_END
            )
        )
        val actual: String = grammarMatcher
            .rule(grammar, SCRIPT_EXAMPLE_1)
            .match()

        assertThat(actual.trimWhiteSpace()).isEqualTo(SCRIPT_EXAMPLE_1.trimWhiteSpace())
    }

    private fun String.trimWhiteSpace(): String {
        return this.filterNot { it.isWhitespace() }
    }

    companion object {
        const val SCRIPT_EXAMPLE_1 = """
            thorak =  {
                name = "Thorak"
            }
        """
    }

}
