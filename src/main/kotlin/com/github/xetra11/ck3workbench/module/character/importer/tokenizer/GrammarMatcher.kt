package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar

/**
 * The grammar parser takes a grammar definition and a list of [ScriptTokenizer.Token]
 * and searches in that for a match. The match is returned as a subset of [ScriptTokenizer.Token]
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
open class GrammarMatcher {
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

    open fun rule(grammar: Grammar, scriptLines: List<String>): MatcherResult {
        return if (grammar.tokenDefinition.isEmpty()) {
            MatcherResult("", hasError = true, errorReason = "Grammar was undefined")
        } else {
            var formattedLines = scriptLines.prepareScriptString()

            val matchCollector = grammar.tokenDefinition.mapTo(mutableListOf<String>()) { tokenType ->
                val regex = tokenRegexMapping[tokenType]
                val match = regex?.find(formattedLines[NEXT])?.value ?: ""
                if (match.isEmpty()) {
                    if (tokenType is TokenType) {
                        return MatcherResult("", hasError = true, errorReason = "Token order invalid")
                    }
                }
                // reduce origin script by matched value
                formattedLines[NEXT] = formattedLines[NEXT].replaceFirst(match, "")
                formattedLines = formattedLines.filter { it.isNotBlank() }.toMutableList()

                match
            }

            MatcherResult(matchCollector.joinToString("") { it })
        }
    }

    private fun List<String>.prepareScriptString(): MutableList<String> {
        return this
            .asSequence()
            .map { it.replace(" ", "") }
            .map { it.replace("\\uFEFF", "") } // remove BOM
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .toMutableList()
    }

    data class MatcherResult(
        val match: String,
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
