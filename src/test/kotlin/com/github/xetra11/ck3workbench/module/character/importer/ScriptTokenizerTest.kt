package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.Token
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ScriptTokenizerTest {
    private val scriptTokenizer: ScriptTokenizer = ScriptTokenizer()

    @Test
    fun `should return a simple tokenization for empty character script definition`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_1)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.L_BRACE),
            Token("}", TokenType.R_BRACE)
        )
    }

    fun `should fail if script has null value left of assignment`(){
        val actual = scriptTokenizer.tokenize(NO_LEFT_VALUE)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.L_BRACE),
            Token("}", TokenType.R_BRACE)
        )
    }

    fun `should fail if script has null value right of assignment`(){
        val actual = scriptTokenizer.tokenize(NO_RIGHT_VALUE)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.L_BRACE),
            Token("}", TokenType.R_BRACE)
        )
    }



    companion object {
        const val TEST_SCRIPT_1 = """
            thorak =  { }
        """
        const val NO_LEFT_VALUE = """
            =  {
            }
        """
        const val NO_RIGHT_VALUE = """
            thorak =
        """
    }
}


