package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.Token
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
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

    @Test
    fun `should return a tokenization for a character script definition containing an attribute identifier`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_2)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.L_BRACE),
            Token("name", TokenType.ATTRIBUTE_IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("}", TokenType.R_BRACE)
        )
    }

    @Test
    fun `should return a tokenization for a character script definition containing an attribute value`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_3)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.L_BRACE),
            Token("=", TokenType.ASSIGNMENT),
            Token("value", TokenType.ATTRIBUTE_VALUE),
            Token("}", TokenType.R_BRACE)
        )
    }

    @Test
    fun `should return a tokenization for a characer script definition with one attribute`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_4)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.L_BRACE),
            Token("name", TokenType.ATTRIBUTE_IDENTIFIER),
            Token("=", TokenType.ASSIGNMENT),
            Token("\"Thorak\"", TokenType.ATTRIBUTE_VALUE),
            Token("}", TokenType.R_BRACE)
        )
    }

    @Test
    fun `should fail if script has null value left of assignment`(){
        assertThatExceptionOfType(ScriptTokenizerError::class.java).isThrownBy {
            scriptTokenizer.tokenize(NO_LEFT_VALUE)
        }
    }

    @Test
    fun `should fail if script has null value right of assignment`(){
        assertThatExceptionOfType(ScriptTokenizerError::class.java).isThrownBy {
            scriptTokenizer.tokenize(NO_RIGHT_VALUE)
        }
    }

    companion object {
        const val TEST_SCRIPT_1 = """
            thorak =  { }
        """
        const val TEST_SCRIPT_2 = """
            thorak =  {
                name = 
            }
        """
        const val TEST_SCRIPT_3 = """
            thorak =  {
                = value
            }
        """
        const val TEST_SCRIPT_4 = """
            thorak =  {
                name = "Thorak"
            }
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


