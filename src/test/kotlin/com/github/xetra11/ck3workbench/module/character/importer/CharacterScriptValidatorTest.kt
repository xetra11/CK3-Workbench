package com.github.xetra11.ck3workbench.module.character.importer

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CharacterScriptValidatorTest {
    private val characterScriptValidator: CharacterScriptValidator = CharacterScriptValidator()

    @Test
    fun `should resolve script file as valid if all brackets are closed`() {
        var actual = characterScriptValidator.validate(VALID_BRACKET_FIXTURE)
        assertThat(actual.hasErrors).isFalse
        actual = characterScriptValidator.validate(VALID_BRACKET_FIXTURE_2)
        assertThat(actual.hasErrors).isFalse
    }

    @Test
    fun `should resolve script file as invalid if not all brackets are closed`() {
        var actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE)
        assertThat(actual.hasErrors).isTrue
        assertThat(actual.errors[0].reason).isEqualTo("missing brackets")
        actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE_2)
        assertThat(actual.hasErrors).isTrue
        assertThat(actual.errors[0].reason).isEqualTo("missing brackets")
        actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE_3)
        assertThat(actual.hasErrors).isTrue
        assertThat(actual.errors[0].reason).isEqualTo("missing brackets")
        actual = characterScriptValidator.validate(INVALID_BRACKET_FIXTURE_COMPLEX)
        assertThat(actual.hasErrors).isTrue
        assertThat(actual.errors[0].reason).isEqualTo("missing brackets")
    }

    @Test
    fun `should resolve script file as invalid if block without id assignment`() {
        val actual = characterScriptValidator.validate(INVALID_MISSING_ID)
        assertThat(actual.hasErrors).isTrue
        assertThat(actual.errors[0].reason).isEqualTo("missing historical id")
    }

    @Test
    fun `should resolve script file as invalid if assignment without id identifier `() {
        val actual = characterScriptValidator.validate(INVALID_MISSING_ID_2)
        assertThat(actual.hasErrors).isTrue
        assertThat(actual.errors[0].reason).isEqualTo("missing historical id")
    }

    @Test
    fun `should resolve as valid with given test data`() {
        val actual = characterScriptValidator.validate(VALID_FIXTURE_WITH_ATTR)
        assertThat(actual.hasErrors).isFalse
    }

    fun `should return multiple errors`() {}

    companion object {
        const val VALID_FIXTURE_WITH_ATTR =
            """
            thorak = {
                name = "Thorak"
                dna = thorak_dna
                dynasty = lineage_of_donar
            }
        """
        const val VALID_BRACKET_FIXTURE =
            """
            name = {
            }
        """
        const val VALID_BRACKET_FIXTURE_2 =
            """
            name = {
                foo = {
                   bar = { 
                   } 
                }
            }
        """
        const val INVALID_BRACKET_FIXTURE =
            """
            name = {
        """
        const val INVALID_BRACKET_FIXTURE_2 =
            """
            name =
             }
        """
        const val INVALID_BRACKET_FIXTURE_3 =
            """
            name = {
                foo = {
                    bar = {
                }
             }
        """

        const val INVALID_BRACKET_FIXTURE_COMPLEX =
            """
            name = {
                foo = {
                    bar = {
                    }
             }
        """

        const val INVALID_MISSING_ID =
            """
            {
                foo = {
                    bar = {
                    }
                }
             }
        """

        const val INVALID_MISSING_ID_2 =
            """
            = {
                foo = {
                    bar = { }
                }
             }
        """
    }
}
