package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.MatcherResult
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.OptionalTokenType
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.ASSIGNMENT
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.ATTRIBUTE_ID
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.ATTRIBUTE_VALUE
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.BLOCK_END
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.BLOCK_START
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher.TokenType.OBJECT_ID
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class GrammarMatcherTest {
    private val grammarMatcher: GrammarMatcher = GrammarMatcher()

    @Test
    fun `should return matched grammar for different order position`() {
        val grammar = Grammar(
            "SUBOBJECT",
            listOf(
                OBJECT_ID, ASSIGNMENT, BLOCK_START, // object_id = {
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // attribute_id = attribute_value
                OBJECT_ID, ASSIGNMENT, BLOCK_START, // object_id = {
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // attribute_id = attribute_value
                BLOCK_END, // }
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // attribute_id = attribute_value
                BLOCK_END // }
            )
        )

        val actual = grammarMatcher
            .rule(grammar, SCRIPT_FULL_EXAMPLE_WITH_SUBOBJECT.split("\n"))
        val expected = MatcherResult(SCRIPT_FULL_EXAMPLE_WITH_SUBOBJECT.trimWhiteSpace())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return matched grammar for optional definitions exclusive`() {
        val grammar = Grammar(
            "OBJECT",
            listOf(
                OBJECT_ID, ASSIGNMENT, BLOCK_START, // object_id = {
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // (attribute_id = attribute_value)
                OptionalTokenType.ATTRIBUTE_ID, OptionalTokenType.ASSIGNMENT, OptionalTokenType.ATTRIBUTE_VALUE, // attribute_id = attribute_value
                BLOCK_END // }
            )
        )

        val actual = grammarMatcher
            .rule(grammar, SCRIPT_WITH_OPTIONAL_EXCLUSIVE.split("\n"))
        val expected = MatcherResult(SCRIPT_WITH_OPTIONAL_EXCLUSIVE.trimWhiteSpace())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return matched grammar for optional definitions inclusive`() {
        val grammar = Grammar(
            "OBJECT",
            listOf(
                OBJECT_ID, ASSIGNMENT, BLOCK_START, // object_id = {
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // attribute_id = attribute_value
                OptionalTokenType.ATTRIBUTE_ID, OptionalTokenType.ASSIGNMENT, OptionalTokenType.ATTRIBUTE_VALUE, // attribute_id = attribute_value
                BLOCK_END // }
            )
        )

        val actual = grammarMatcher
            .rule(grammar, SCRIPT_WITH_OPTIONAL_INCLUSIVE.split("\n"))
        val expected = MatcherResult(SCRIPT_WITH_OPTIONAL_INCLUSIVE.trimWhiteSpace())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return the matched string of a script string with full example attributes and sub object`() {
        val grammar = Grammar(
            "SUBOBJECT",
            listOf(
                OBJECT_ID, ASSIGNMENT, BLOCK_START, // object_id = {
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // attribute_id = attribute_value
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // attribute_id = attribute_value
                OBJECT_ID, ASSIGNMENT, BLOCK_START, // object_id = {
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, // attribute_id = attribute_value
                BLOCK_END, // }
                BLOCK_END // }
            )
        )
        val actual = grammarMatcher
            .rule(grammar, SCRIPT_FULL_EXAMPLE_WITH_SUBOBJECT.split("\n"))
        val expected = MatcherResult(SCRIPT_FULL_EXAMPLE_WITH_SUBOBJECT.trimWhiteSpace())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return the matched string of a script string with full example attributes`() {
        val grammar = Grammar(
            "FULL_BOOM",
            listOf(
                OBJECT_ID, ASSIGNMENT, BLOCK_START,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                BLOCK_END
            )
        )
        val actual = grammarMatcher
            .rule(grammar, SCRIPT_FULL_EXAMPLE.split("\n"))
        val expected = MatcherResult(SCRIPT_FULL_EXAMPLE.trimWhiteSpace())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return the matched string of a script string with two attributes and dirty intendenation`() {
        val grammar = Grammar(
            "SCRIPT",
            listOf(
                OBJECT_ID, ASSIGNMENT, BLOCK_START,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE,
                BLOCK_END
            )
        )
        val actual = grammarMatcher
            .rule(grammar, SCRIPT_EXAMPLE_2.split("\n"))
        val expected = MatcherResult(SCRIPT_EXAMPLE_2.trimWhiteSpace())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return the scriptstring if the given test script is matching`() {
        val grammar = Grammar(
            "SCRIPT",
            listOf(OBJECT_ID, ASSIGNMENT, BLOCK_START, ATTRIBUTE_ID, ASSIGNMENT, ATTRIBUTE_VALUE, BLOCK_END)
        )
        val actual = grammarMatcher
            .rule(grammar, SCRIPT_EXAMPLE_1.split("\n"))
        val expected = MatcherResult(SCRIPT_EXAMPLE_1.trimWhiteSpace())

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return the string if the given test script is having only a matching OBJECT_ID`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID)
        )
        val actual: String = grammarMatcher
            .rule(grammar, TEST_1.split("\n"))
            .match

        assertThat(actual.trimWhiteSpace()).isEqualTo(TEST_1.trimWhiteSpace())
    }

    @Test
    fun `should return the string if the given test script is having only a matching OBJECT_ID and ASSIGNMENT`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID, ASSIGNMENT)
        )
        val actual: String = grammarMatcher
            .rule(grammar, TEST_2.split("\n"))
            .match

        assertThat(actual.trimWhiteSpace()).isEqualTo(TEST_2.trimWhiteSpace())
    }

    @Test
    fun `should return the string if the given test script is  matching OBJECT_ID, ASSIGNMENT, BLOCK_START`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID, ASSIGNMENT, BLOCK_START)
        )
        val actual: String = grammarMatcher
            .rule(grammar, TEST_3.split("\n"))
            .match

        assertThat(actual.trimWhiteSpace()).isEqualTo(TEST_3.trimWhiteSpace())
    }

    @Test
    fun `should return an error if given script tokens won't match with grammar`() {
        val grammar = Grammar(
            "TEST",
            listOf(OBJECT_ID, ASSIGNMENT, BLOCK_START)
        )
        val actual = grammarMatcher
            .rule(grammar, INVALID_1.split("\n"))

        assertThat(actual.hasError).isTrue
        assertThat(actual.errorReason).isEqualTo("Token order invalid")
        assertThat(actual.match).isEqualTo("")
    }

    @Test
    fun `should throw an error if given grammar is empty`() {
        val grammar = Grammar(
            "SCRIPT",
            listOf()
        )
        val actual = grammarMatcher
            .rule(grammar, SCRIPT_EXAMPLE_1.split("\n"))

        assertThat(actual.hasError).isTrue
        assertThat(actual.errorReason).isEqualTo("Grammar was undefined")
        assertThat(actual.match).isEqualTo("")
    }

    private fun String.trimWhiteSpace(): String {
        return this.filterNot { it.isWhitespace() }
    }

    companion object {
        const val TEST_1 =
            """
            thorak
        """
        const val TEST_2 =
            """
            thorak =
        """
        const val TEST_3 =
            """
            thorak = {
        """
        const val INVALID_1 =
            """
            thorak {
        """
        const val SCRIPT_EXAMPLE_1 =
            """
            thorak =  {
                name = "Thorak"
            }
        """
        const val SCRIPT_EXAMPLE_2 =
            """
            thorak =     {
                name =     "Thorak"
                dna =  thorak_dna 
                culture = cheruscii
                
                
                
            }
        """
        const val SCRIPT_FULL_EXAMPLE =
            """
                thorak = {
                    name = "Thorak"
                    dna = thorak_dna
                    dynasty = lineage_of_donar
                    religion = "norse_pagan"
                    culture = cheruscii
                    martial = 7
                    stewardship = 6
                    diplomacy = 4
                    intrigue = 5
                    learning = 3
                    trait = ambitious
                    trait = hunter_1
                    trait = wrathful
                    trait = callous
                    trait = viking
                    trait = education_martial_3
                }
        """

        const val SCRIPT_FULL_EXAMPLE_WITH_SUBOBJECT =
            """
                thorak = {
                    name = "Thorak"
                    dna = thorak_dna
                    731.11 = {
                        birth = yes
                    }
                }
        """

        const val SCRIPT_WITH_OPTIONAL_INCLUSIVE =
            """
                thorak = {
                    name = "Thorak"
                    dna = thorak_dna
                }
        """
        const val SCRIPT_WITH_OPTIONAL_EXCLUSIVE =
            """
                thorak = {
                    name = "Thorak"
                }
        """
    }
}
