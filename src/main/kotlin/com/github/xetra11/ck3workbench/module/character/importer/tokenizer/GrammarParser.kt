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
    private val resolverDictionary: MutableMap<String, MutableList<String>> = mutableMapOf()
    private val _grammars: MutableList<Grammar> = mutableListOf()
    val grammars: List<Grammar> = _grammars

    fun process(grammarInput: String) {
        val rawGrammars = grammarInput
            .trim()
            .split("---")

        val processedGrammars = mutableListOf<Grammar>()

        rawGrammars.forEach { grammar ->
            val lines = extractGrammarInputLines(grammar)
            val definition = extractDefinitionName(lines)
            val tokenChain = extractTokenChain(lines)
            val tokens = extractTokens(tokenChain)

            initResolverDictionary(definition)

            val typedTokens = tokens
                .quantify()
                .map { token ->
                    resolverDictionary[definition]?.add(token)
                    token
                }
                .resolve()
                .map {
                    when (it) {
                        "[ATTRIBUTE_ID]" -> TokenType.ATTRIBUTE_ID
                        "[OBJECT_ID]" -> TokenType.OBJECT_ID
                        "[BLOCK_START]" -> TokenType.BLOCK_START
                        "[BLOCK_END]" -> TokenType.BLOCK_END
                        "[ASSIGNMENT]" -> TokenType.ASSIGNMENT
                        "[ATTRIBUTE_VALUE]" -> TokenType.ATTRIBUTE_VALUE
                        else -> TokenType.UNTYPED
                    }
                }

            // add to outer scope for .resolver access to existing processed grammars
            this._grammars.add(Grammar(definition, typedTokens))
        }
    }

    private fun extractGrammarInputLines(grammar: String): List<String> {
        return grammar.lines()
            .map { it.trimWhiteSpace() }
    }

    private fun extractTokens(tokenChain: String) = tokenChain.trim().split(".")

    private fun extractTokenChain(lines: List<String>): String {
        return lines.filterNot { it.startsWith(":") }.joinToString("")
    }

    private fun extractDefinitionName(lines: List<String>): String {
        return lines.first { it.startsWith(":") }.replace(":", "")
    }

    private fun initResolverDictionary(definition: String) {
        resolverDictionary.putIfAbsent(definition, mutableListOf())
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
    private fun Iterable<String>.resolve(): List<String> {
        return this.flatMap { token ->
            if (!isDefinitionToken(token)) {
                listOf(token)
            } else {
                val identifierMatcher = Regex("([A-Z])\\w+")
                val name = identifierMatcher.find(token)?.value
                resolverDictionary[name] ?: listOf(token)
            }
        }
    }

    private fun isDefinitionToken(token: String): Boolean {
        return token.startsWith("[:")
    }

    private fun String.trimWhiteSpace(): String {
        return this.filterNot { it.isWhitespace() }
    }

    data class Grammar(
        val definitionName: String,
        val tokenDefinition: List<TokenType>
    )

    inner class UntypedGrammar(
        val definitionName: String,
        val untypedToken: List<String>
    )

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(GrammarParser::class.java)
    }
}
