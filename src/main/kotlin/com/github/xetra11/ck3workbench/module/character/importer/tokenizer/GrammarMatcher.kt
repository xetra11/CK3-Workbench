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
        OBJECT_ID to Regex("^(\\w+)"),
        ASSIGNMENT to Regex("^="),
        BLOCK_START to Regex("^\\{")
    )

    fun rule(grammar: Grammar, script: String): MatcherResult {
        var formattedScript = script.trimWhiteSpace()
        val matchCollector = mutableListOf<String>()

        val regexObjectId = tokenRegexMapping[OBJECT_ID]
        var value = regexObjectId?.find(formattedScript)?.value ?: ""
        matchCollector.add(value)
        formattedScript = formattedScript.replace(value, "")

        val regexAssignment = tokenRegexMapping[ASSIGNMENT]
        value = regexAssignment?.find(formattedScript)?.value ?: ""
        if (value.isEmpty()) {
            return MatcherResult("", hasError = true, errorReason = "Token order invalid")
        }
        matchCollector.add(value)
        formattedScript = formattedScript.replace(value, "")

        val regexBlockStart = tokenRegexMapping[BLOCK_START]
        value = regexBlockStart?.find(formattedScript)?.value ?: ""
        matchCollector.add(value)
        formattedScript = formattedScript.replace(value, "")

        return MatcherResult(matchCollector.joinToString("") { it })
    }

    private fun String.trimWhiteSpace(): String {
        return this.filterNot { it.isWhitespace() }
    }

    class MatcherResult(
        val match: String,
        val hasError: Boolean = false,
        val errorReason: String = ""
    )
}

