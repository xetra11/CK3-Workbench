package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.github.xetra11.ck3workbench.module.character.importer.ScriptTokenizer.TokenType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

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
                .filter { it.isNotBlank() }
                .map { it.trim() }
            // :<definition>
            val definition = lines.first { it.startsWith(":") }.replace(":", "")
            // [<token>].[<token2>].[<token3>]
            val tokenChain = lines.filterNot { it.startsWith(":") }.joinToString("")
            val tokens = tokenChain.trim().split(".")

            val typedTokens = tokens
                .quantify()
                .map {
                    when (it) {
                        "[ATTRIBUTE_ID]" -> TokenType.ATTRIBUTE_ID
                        "[OBJECT_ID]" -> TokenType.OBJECT_ID
                        "[ASSIGNMENT]" -> TokenType.ASSIGNMENT
                        "[ATTRIBUTE_VALUE]" -> TokenType.ATTRIBUTE_VALUE
                        else -> TokenType.UNTYPED
                    }
                }

            Grammar(definition, typedTokens)
        }
    }

    // multiply definition depending on its quantity modifier
    // Example: [DEFINITION]*3
    private fun Iterable<String>.quantify(): List<String> {
        return this.flatMap { token ->
            if (!token.contains("*")) {
               listOf(token)
            } else {
                try {
                    val quantity = token.split("*")[1].toInt()
                    token
                        .repeat(quantity)
                        .split("*$quantity")
                        .filter { it.isNotBlank() }
                } catch (e: NumberFormatException) {
                    LOG.error("Quantity modifier is not a number")
                    listOf(token)
                }
            }

        }
    }

    // resolve references with their definitions
    // private fun Iterable<String>.resolve(): List<String> {
    //     return this.flatMap { reference ->
    //     }
    // }

    data class Grammar(
        val definitionName: String,
        val tokenDefinition: List<TokenType>
    )

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(GrammarParser::class.java)
    }
}
