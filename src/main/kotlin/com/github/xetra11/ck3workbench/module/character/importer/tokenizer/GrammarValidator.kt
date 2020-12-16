package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar

/**
 * The grammar parser takes a grammar definition and a list of [ScriptTokenizer.Token]
 * and searches in that for a match. The match is returned as a subset of [ScriptTokenizer.Token]
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
open class GrammarValidator {
    private val tokenRegexMapping: Map<TokenDefinition, Regex> = mapOf(
        TokenType.OBJECT_ID to Regex("(.(\\w+\\.)*\\w*)"),
        TokenType.ASSIGNMENT to Regex("^="),
        TokenType.ATTRIBUTE_ID to Regex("^(\\w+)"),
        TokenType.ATTRIBUTE_VALUE to Regex("^(\"?\\w+\"?)"),
        TokenType.BLOCK_START to Regex("^\\{"),
        TokenType.BLOCK_END to Regex("^}"),

        OptionalTokenType.OBJECT_ID to Regex("(.(\\w+\\.)*\\w*)"),
        OptionalTokenType.ASSIGNMENT to Regex("^="),
        OptionalTokenType.ATTRIBUTE_ID to Regex("^(\\w+)"),
        OptionalTokenType.ATTRIBUTE_VALUE to Regex("^(\"?\\w+\"?)"),
        OptionalTokenType.BLOCK_START to Regex("^\\{"),
        OptionalTokenType.BLOCK_END to Regex("^}")
    )

    open fun rule(grammar: Grammar, scriptLines: List<String>): GrammarValidation {
        return if (grammar.tokenDefinition.isEmpty()) {
            GrammarValidation(hasError = true, errorReason = "Grammar was undefined")
        } else {
            val formattedLines = scriptLines.prepareScriptString()
            val nestedGrammar = nestGrammar(grammar)

            var mandatoryTokenToFulfill = nestedGrammar.mandatoryToken().count()
            var currentLevel = 0

            val scriptValid = formattedLines.all { line ->
                val pieces = line.split(" ").filter { it.isNotBlank() }
                pieces.all { piece ->
                    when (piece) {
                        "{" -> {
                            currentLevel++
                            true
                        }
                        "}" -> {
                            currentLevel--
                            true
                        }
                        else -> {
                            mandatoryTokenToFulfill--
                            val grammarSection = nestedGrammar.level(currentLevel)
                            val tokenRegexCandidates = grammarSection
                                .mapTo(mutableListOf<Regex>()) { tokenRegexMapping[it] ?: Regex("") }
                            tokenRegexCandidates.any { it.matches(piece) }
                        }
                    }
                }
            }
            if (!scriptValid) {
                GrammarValidation(true, "Some token are not matching the given grammar")
            } else if (mandatoryTokenToFulfill > 0) {
                GrammarValidation(true, "Not all mandatory token were matched. $mandatoryTokenToFulfill were left out")
            } else {
                GrammarValidation()
            }
        }
    }

    private fun nestGrammar(grammar: Grammar): GrammarNester.NestedGrammar {
        val grammarNester = GrammarNester()
        return grammarNester.nest(grammar)
    }

    private fun List<String>.prepareScriptString(): MutableList<String> {
        return this
            .asSequence()
            .map { it.replace("\\uFEFF", "") } // remove BOM
            .map { it.trimStart() }
            .map { it.trimEnd() }
            .filter { it.isNotBlank() }
            .toMutableList()
    }

    data class GrammarValidation(
        val hasError: Boolean = false,
        val errorReason: String = ""
    )

    companion object {
        const val NEXT = 0
    }

    interface TokenDefinition

    enum class TokenType : TokenDefinition {
        OBJECT_ID,
        ATTRIBUTE_ID,
        ATTRIBUTE_VALUE,
        BLOCK_START,
        BLOCK_END,
        ASSIGNMENT,
        UNTYPED
    }

    enum class OptionalTokenType : TokenDefinition {
        OBJECT_ID,
        ATTRIBUTE_ID,
        ATTRIBUTE_VALUE,
        BLOCK_START,
        BLOCK_END,
        ASSIGNMENT,
        UNTYPED
    }
}
