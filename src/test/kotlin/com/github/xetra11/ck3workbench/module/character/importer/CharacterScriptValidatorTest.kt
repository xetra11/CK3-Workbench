package com.github.xetra11.ck3workbench.module.character.importer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CharacterScriptValidatorTest {
    private val characterScriptValidator: CharacterScriptValidator = CharacterScriptValidator()

    @Test
    fun `should resolve script file as valid if all brackets are closed`() {
        var actual = characterScriptValidator.validate(VALID_BRACKET_FIXTURE)
        assertThat(actual).isTrue
        actual = characterScriptValidator.validate(VALID_BRACKET_FIXTURE_2)
        assertThat(actual).isTrue
    }

    @Test
    fun `should resolve script file as invalid if not all brackets are closed`() {
        var actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE)
        assertThat(actual).isFalse
        actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE_2)
        assertThat(actual).isFalse
        actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE_3)
        assertThat(actual).isFalse
        actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE_COMPLEX)
        assertThat(actual).isFalse
    }

    companion object {
        const val VALID_BRACKET_FIXTURE = """
            name = {
            }
        """
        const val VALID_BRACKET_FIXTURE_2 = """
            name = {
                foo = {
                   bar = { 
                   } 
                }
            }
        """
        const val INVALID_BRACKET_FIXTURE = """
            name = {
        """
        const val INVALID_BRACKET_FIXTURE_2 = """
            name =
             }
        """
        const val INVALID_BRACKET_FIXTURE_3 = """
            name = {
                foo = {
                    bar = {
                }
             }
        """

        const val INVALID_BRACKET_FIXTURE_COMPLEX = """
            name = {
                foo = {
                    bar = {
                    }
             }
        """
    }
}
