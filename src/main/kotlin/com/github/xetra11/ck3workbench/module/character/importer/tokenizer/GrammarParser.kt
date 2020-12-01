package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.Token
import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import java.util.LinkedList

/**
 * Reads a grammar file and returns the grammar definition for it
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class GrammarParser {
    fun parse(grammarInput: String): List<Grammar> {
        val grammars = grammarInput
            .trimStart()
            .trimEnd()
            .split("---")

        return grammars.map { grammar ->
            val lines = grammar.lines()
            val grammarScope = lines.first { it.startsWith(":") }.replace(":","")
            val grammarDefinitions = lines.filterNot { it.startsWith(":") }.joinToString("")
            val definitions = grammarDefinitions.trim().split(".")
            val tokenTypes = toTokenTypes(definitions)

            Grammar(grammarScope, LinkedHashSet(tokenTypes))
        }
    }

    private fun toTokenTypes(definitions: List<String>): List<TokenType> {
        return definitions.map {
            when(it) {
                "[ATTRIBUTE_ID]" -> TokenType.ATTRIBUTE_ID
                "[ASSIGNMENT]" -> TokenType.ASSIGNMENT
                "[ATTRIBUTE_VALUE]" -> TokenType.ATTRIBUTE_VALUE
                else -> TokenType.UNTYPED
            }
        }
    }

    class Grammar(
        val scope: String,
        val tokenDefinition: LinkedHashSet<TokenType>
    )
}
