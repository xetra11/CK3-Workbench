package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.Token
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.File

internal class ScriptTokenizerTest {
    private val scriptTokenizer: ScriptTokenizer = ScriptTokenizer()

    @Test
    fun `should return a section representing a character script definition`(){
        val actual = scriptTokenizer.tokenize(TEST_SCRIPT_1)

        assertThat(actual).element(0).isEqualTo(Token("thorak", TokenType.UNTYPED))
        assertThat(actual).element(1).isEqualTo(Token("=", TokenType.ASSIGNMENT))
        assertThat(actual).element(2).isEqualTo(Token("{", TokenType.L_BRACE))
        assertThat(actual).element(3).isEqualTo(Token("}", TokenType.R_BRACE))
    }

    companion object {
        const val TEST_SCRIPT_1 = """
            thorak =  {
            }
        """
    }
}


