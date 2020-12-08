package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType.*
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar

/**
 * The grammar parser takes a grammar definition and a list of [ScriptTokenizer.Token]
 * and searches in that for a match. The match is returned as a subset of [ScriptTokenizer.Token]
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class GrammarMatcher {
    private val tokenRegexMapping: Map<TokenType, Regex> = mapOf(
        OBJECT_ID to Regex("^(\\w+\\.*\\w*)"),
        ASSIGNMENT to Regex("^="),
        ATTRIBUTE_ID to Regex("^(\\w+)"),
        ATTRIBUTE_VALUE to Regex("^(\"?\\w+\"?)"),
        BLOCK_START to Regex("^\\{"),
        BLOCK_END to Regex("^}")
    )

    fun rule(grammar: Grammar, scriptLines: List<String>): MatcherResult {
        if (grammar.tokenDefinition.isEmpty()) {
            return MatcherResult("", hasError = true, errorReason = "Grammar was undefined")
        }

        var formattedLines = scriptLines.prepareScriptString()

        val matchCollector = grammar.tokenDefinition.mapTo(mutableListOf<String>()) { tokenType ->
            val regex = tokenRegexMapping[tokenType]
            val match = regex?.find(formattedLines[NEXT])?.value ?: ""
            if (match.isEmpty()) {
                return MatcherResult("", hasError = true, errorReason = "Token order invalid")
            }
            // reduce origin script by matched value
            formattedLines[NEXT] = formattedLines[NEXT].replaceFirst(match, "")
            formattedLines = formattedLines.filter { it.isNotBlank() }.toMutableList()

            match
        }

        return MatcherResult(matchCollector.joinToString("") { it })
    }

    private fun List<String>.prepareScriptString(): MutableList<String> {
        return this
            .map { it.replace(" ", "")}
            .map { it.replace("\\uFEFF", "")} // remove BOM
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
}

