package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GrammarParserTest {
    private val grammarParser: GrammarParser = GrammarParser()

    @Test
    fun `should read simple grammar file and return a grammar definition`() {
        val grammars = grammarParser.parse(GRAMMAR_FILE_1)

        assertThat(grammars).containsExactly(
            Grammar(
                "ATTRIBUTE", listOf(
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE
                )
            ),
            Grammar(
                "OBJECT", listOf(
                    TokenType.OBJECT_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE
                )
            ),
        )
    }

    @Test
    fun `should read simple grammar file with multiplier and return a grammar definition`() {
        val grammars = grammarParser.parse(MULTI_TEST)

        assertThat(grammars).containsExactly(
            Grammar(
                "MULTI", listOf(
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE
                )
            )
        )
    }

    @Test
    fun `should read grammar file with nested attributes and return a grammar definition`() {
        val grammars = grammarParser.parse(GRAMMAR_FILE_2)

        assertThat(grammars).containsExactly(
            Grammar(
                "ATTRIBUTE", listOf(
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE
                )
            ),
            Grammar(
                "OBJECT", listOf(
                    TokenType.OBJECT_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.BLOCK_START,
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE,
                    TokenType.BLOCK_END
                )
            )
        )
    }

    fun `flatmap test`() {
        val actual = listOf("A", "B", "C*2")
            .flatMap { token ->
                if (token.contains("*")) {
                    try {
                        val quantity = token.split("*")[1].toInt()
                        token
                            .repeat(quantity)
                            .split("*$quantity")
                            .filter { it.isNotBlank() }
                    } catch (e: NumberFormatException) {
                        //stuff
                    }
                }
                listOf(token)
            }

        assertThat(actual).contains(
            "A", "B", "C", "C"
        )
    }

    fun `should add mandatory modifier`() {}

    companion object {
        const val GRAMMAR_FILE_1 = """
            :ATTRIBUTE
            [ATTRIBUTE_ID].[ASSIGNMENT].[ATTRIBUTE_VALUE]
            ---
            :OBJECT
            [OBJECT_ID].[ASSIGNMENT].[ATTRIBUTE_VALUE]
        """
        const val MULTI_TEST = """
            :MULTI
            [ATTRIBUTE_ID]*2.[ASSIGNMENT].[ATTRIBUTE_VALUE]
        """
        const val GRAMMAR_FILE_2 = """
            :ATTRIBUTE
            [ATTRIBUTE_ID].[ASSIGNMENT].[ATTRIBUTE_VALUE]
            ---
            :OBJECT
            [OBJECT_ID].[ASSIGNMENT].[BLOCK_START].[:ATTRIBUTE]*1.[BLOCK_END]
        """
    }
}
