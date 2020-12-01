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
                "ATTRIBUTE", linkedSetOf(
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE
                )
            ),
            Grammar(
                "OBJECT", linkedSetOf(
                    TokenType.OBJECT_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE
                )
            ),
        )
    }

    @Test
    fun `should read grammar file with nested attributes and return a grammar definition`() {
        val grammars = grammarParser.parse(GRAMMAR_FILE_2)

        assertThat(grammars).containsExactly(
            Grammar(
                "ATTRIBUTE", linkedSetOf(
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE
                )
            ),
            Grammar(
                "OBJECT", linkedSetOf(
                    TokenType.OBJECT_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE,
                    TokenType.BLOCK_START,
                    TokenType.ATTRIBUTE_ID,
                    TokenType.ASSIGNMENT,
                    TokenType.ATTRIBUTE_VALUE,
                    TokenType.BLOCK_END
                )
            )
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
        const val GRAMMAR_FILE_2 = """
            :ATTRIBUTE
            [ATTRIBUTE_ID].[ASSIGNMENT].[ATTRIBUTE_VALUE]
            ---
            :OBJECT
            [OBJECT_ID].[ASSIGNMENT].[BLOCK_START].[:ATTRIBUTE]*1.[BLOCK_END]
        """
    }
}
