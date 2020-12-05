package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType.OBJECT_ID
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar

/**
 * The grammar parser takes a grammar definition and a list of [ScriptTokenizer.Token]
 * and searches in that for a match. The match is returned as a subset of [ScriptTokenizer.Token]
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class GrammarMatcher {
    private val tokenRegexMapping: Map<TokenType, Regex> = mapOf(
        OBJECT_ID to Regex("^(\\w+)")
    )

    fun rule(grammar: Grammar, script: String): MatcherResult {
        val formattedScript = script.trimWhiteSpace()
        val regex = tokenRegexMapping[OBJECT_ID]
        val matchedValue = regex?.find(formattedScript)?.value

        return MatcherResult(matchedValue ?: "")
    }

    private fun String.trimWhiteSpace(): String {
        return this.filterNot { it.isWhitespace() }
    }

    class MatcherResult(
        val match: String
    )
}

