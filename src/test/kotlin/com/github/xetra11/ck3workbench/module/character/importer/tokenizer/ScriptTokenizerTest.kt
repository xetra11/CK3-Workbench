package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.Token
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizerError
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Test

internal class ScriptTokenizerTest {
    private val scriptTokenizer: ScriptTokenizer = ScriptTokenizer()

    @Test
    fun `should return a simple tokenization for empty character script definition`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_1)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.OBJECT_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.BLOCK_START),
            Token("}", TokenType.BLOCK_END)
        )
    }

    @Test
    fun `should return a tokenization for a character script definition containing an attribute identifier`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_2)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.OBJECT_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.BLOCK_START),
            Token("name", TokenType.ATTRIBUTE_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("}", TokenType.BLOCK_END)
        )
    }

    @Test
    fun `should return a tokenization for a character script definition containing an attribute value`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_3)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.OBJECT_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.BLOCK_START),
            Token("=", TokenType.ASSIGNMENT),
            Token("value", TokenType.ATTRIBUTE_VALUE),
            Token("}", TokenType.BLOCK_END)
        )
    }

    @Test
    fun `should return a tokenization for a characer script definition with one attribute`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_4)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.OBJECT_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.BLOCK_START),
            Token("name", TokenType.ATTRIBUTE_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("\"Thorak\"", TokenType.ATTRIBUTE_VALUE),
            Token("}", TokenType.BLOCK_END)
        )
    }

    @Test
    fun `should return a tokenization for a characer script definition with multiple attributes`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_5)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.OBJECT_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.BLOCK_START),
            Token("name", TokenType.ATTRIBUTE_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("\"Thorak\"", TokenType.ATTRIBUTE_VALUE),
            Token("dna", TokenType.ATTRIBUTE_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("thorak_dna", TokenType.ATTRIBUTE_VALUE),
            Token("}", TokenType.BLOCK_END)
        )
    }

    @Test
    fun `should return a tokenization for a character script definition with sub object`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_6)

        assertThat(actual).containsExactly(
            Token("thorak", TokenType.OBJECT_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.BLOCK_START),
            Token("name", TokenType.ATTRIBUTE_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("\"Thorak\"", TokenType.ATTRIBUTE_VALUE),
            Token("dna", TokenType.ATTRIBUTE_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("thorak_dna", TokenType.ATTRIBUTE_VALUE),
            Token("733.1.1", TokenType.OBJECT_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("{", TokenType.BLOCK_START),
            Token("birth", TokenType.ATTRIBUTE_ID),
            Token("=", TokenType.ASSIGNMENT),
            Token("yes", TokenType.ATTRIBUTE_VALUE),
            Token("}", TokenType.BLOCK_END),
            Token("}", TokenType.BLOCK_END)
        )
    }

    fun `should remove comments before tokenizing`(){
        val actual = scriptTokenizer.tokenize(WITH_COMMENT)
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
        const val TEST_SCRIPT_5 = """
            thorak =  {
                name = "Thorak"
                dna = thorak_dna
            }
        """
        const val TEST_SCRIPT_6 = """
            thorak =  {
                name = "Thorak"
                dna = thorak_dna
                
                731.1.1 = { 
                    birth = yes
                }
            }
        """
        const val WITH_COMMENT = """
            thorak =  {
                # Bar Text
                name = "Thorak"
                dna = thorak_dna
                
                731.1.1 = { # Foo
                    birth = yes
                }
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


