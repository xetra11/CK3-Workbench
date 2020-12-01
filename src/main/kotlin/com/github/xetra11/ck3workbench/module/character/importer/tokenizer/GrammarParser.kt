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
            LOG.info(lines.toString())
            // :<definition>
            val definition = lines.first { it.startsWith(":") }.replace(":", "")
            // [<token>].[<token2>].[<token3>]
            val tokenChain = lines.filterNot { it.startsWith(":") }.joinToString("")
            val tokens = tokenChain.trim().split(".")

            val definitionReferences = tokens
                .filter { it.startsWith("[:") }
                .quantify()
                .resolve()


            val tokenTypes = toTokenTypes(tokens)

            Grammar(definition, LinkedHashSet(tokenTypes))
        }
    }

    // multiply definition depending on its quantifier modifier
    private fun Iterable<String>.quantify(): List<String> {
        return this.flatMap { reference ->
            val quantity: Int
            try {
                quantity = reference.split("*")[1].toInt()
                return reference
                    .repeat(quantity)
                    .split("*$quantity")
                    .filter { it.isNotBlank() }
            } catch (e: NumberFormatException) {
                LOG.error("Quantity modifier is not a number")
            }
            listOf()
        }
    }

    // resolve references with their definitions
    private fun Iterable<String>.resolve(): List<String> {
        return this.flatMap { reference ->
        }
    }


    private fun toTokenTypes(definitions: List<String>): List<TokenType> {
        return definitions.map {
            when (it) {
                "[ATTRIBUTE_ID]" -> TokenType.ATTRIBUTE_ID
                "[OBJECT_ID]" -> TokenType.OBJECT_ID
                "[ASSIGNMENT]" -> TokenType.ASSIGNMENT
                "[ATTRIBUTE_VALUE]" -> TokenType.ATTRIBUTE_VALUE
                else -> TokenType.UNTYPED
            }
        }
    }

    data class Grammar(
        val definitionName: String,
        val tokenDefinition: LinkedHashSet<TokenType>
    )

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(GrammarParser::class.java)
    }
}
