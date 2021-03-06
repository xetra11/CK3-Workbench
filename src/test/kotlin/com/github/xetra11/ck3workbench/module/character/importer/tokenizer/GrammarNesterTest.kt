package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarNester
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarParser
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenType.ASSIGNMENT
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenType.ATTRIBUTE_ID
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenType.ATTRIBUTE_VALUE
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenType.BLOCK_END
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenType.BLOCK_START
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenType.OBJECT_ID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GrammarNesterTest {
    private val grammarNester: GrammarNester = GrammarNester()

    @Test
    fun `should return a nested grammar list`() {
        val grammar = GrammarParser.Grammar(
            "OBJECT",
            listOf(
                OBJECT_ID,
                ASSIGNMENT,
                BLOCK_START,
                ATTRIBUTE_ID,
                ASSIGNMENT,
                ATTRIBUTE_VALUE,
                BLOCK_END
            )
        )

        val actual = grammarNester.nest(grammar)
        val expected = GrammarNester.NestedGrammar(
            mapOf(
                0 to listOf(OBJECT_ID, ASSIGNMENT),
                1 to listOf(ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE)
            )
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return a complex nested grammar list`() {
        val grammar = GrammarParser.Grammar(
            "OBJECT",
            listOf(
                OBJECT_ID, ASSIGNMENT, BLOCK_START,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, BLOCK_START,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                BLOCK_END,
                BLOCK_END
            )
        )

        val actual = grammarNester.nest(grammar)
        val expected = GrammarNester.NestedGrammar(
            mapOf(
                0 to listOf(OBJECT_ID, ASSIGNMENT),
                1 to listOf(ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE),
                2 to listOf(
                    ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                    ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                    ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE
                )
            )
        )

        assertThat(actual).isEqualTo(expected)
    }
}
