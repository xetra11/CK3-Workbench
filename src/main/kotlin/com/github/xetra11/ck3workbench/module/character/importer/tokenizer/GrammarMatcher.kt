package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser.Grammar

/**
 * The grammar parser takes a grammar definition and a list of [ScriptTokenizer.Token]
 * and searches in that for a match. The match is returned as a subset of [ScriptTokenizer.Token]
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class GrammarMatcher {
    fun rule(grammar: Grammar, script: String): MatcherResult {
        return MatcherResult()
    }

    class MatcherResult() {
        fun match(): String {
            return ""
        }

    }
}

