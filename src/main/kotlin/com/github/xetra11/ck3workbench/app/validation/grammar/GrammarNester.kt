package com.github.xetra11.ck3workbench.app.validation.grammar

import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarNester.NestedGrammar
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.OptionalTokenType
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenDefinition
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator.TokenType

/**
 * Takes a [List] of [GrammarParser.Grammar] and converts it into [NestedGrammar]
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class GrammarNester {
    private var currentLevel = 0
    private val nestedGrammar: MutableMap<Int, MutableList<TokenDefinition>> = mutableMapOf()

    /**
     * Takes a [List] of [GrammarParser.Grammar] and converts it into [NestedGrammar]
     * @param [GrammarParser.Grammar] to converted to [NestedGrammar]
     * @return [NestedGrammar]
     */
    fun nest(grammar: GrammarParser.Grammar): NestedGrammar {

        grammar.tokenDefinition.forEach { token ->
            require(currentLevel >= 0)

            upLevelCheck(token)
            addToken(token)
            downLevelCheck(token)
        }

        return NestedGrammar(nestedGrammar)
    }

    private fun addToken(token: TokenDefinition) {
        if (isBlock(token)) return // blocks are skipped
        if (nestedGrammar.putIfAbsent(currentLevel, mutableListOf(token)) != null) {
            nestedGrammar[currentLevel]!!.add(token)
        }
    }

    private fun isBlock(token: TokenDefinition): Boolean {
        return (token == TokenType.BLOCK_START || token == OptionalTokenType.BLOCK_START)
            .or(token == TokenType.BLOCK_END || token == OptionalTokenType.BLOCK_END)
    }

    private fun downLevelCheck(token: TokenDefinition) {
        if (token == TokenType.BLOCK_START || token == OptionalTokenType.BLOCK_START) {
            currentLevel++
        }
    }

    private fun upLevelCheck(token: TokenDefinition) {
        if (token == TokenType.BLOCK_END || token == OptionalTokenType.BLOCK_END) {
            currentLevel--
        }
    }

    /**
     * Contains a [GrammarParser.Grammar] and its [TokenDefinition]s nested into levels
     */
    class NestedGrammar(
        private val nestedToken: Map<Int, List<TokenDefinition>>
    ) {

        /**
         * Get the [TokenDefintion]s of the [NestedGrammar] and the given level
         * @param level is depth level of the nested grammar. 0 or greater than the depth
         * of the [NestedGrammar] is not allowed as argument and will throw an [IllegalArgumentException]
         * @return the list of [TokenDefinition] at the given level
         */
        fun level(level: Int): List<TokenDefinition> {
            require((level >= 0).and(level.minus(1) < nestedToken.size))
            return nestedToken[level].orEmpty()
        }

        /**
         * Depth of the [NestedGrammar]
         * @return the depth as number. Root (0) will not be counted as depth.
         */
        fun depth(): Int {
            return nestedToken.size.minus(1)
        }

        /**
         * The root definitions of a [NestedGrammar]
         * @return the first list of [TokenDefinition] at the very beginning
         */
        fun root(): List<TokenDefinition> {
            return nestedToken[0].orEmpty()
        }

        /**
         * The amount of non optional [TokenType] token.
         * @return the amount of mandatory token
         */
        fun mandatoryToken(): List<TokenType> {
            return nestedToken.values
                .flatten()
                .filterIsInstance<TokenType>()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as NestedGrammar

            if (nestedToken != other.nestedToken) return false

            return true
        }

        override fun hashCode(): Int {
            return nestedToken.hashCode()
        }

        override fun toString(): String {
            return "NestedGrammar(nestedToken=$nestedToken)"
        }
    }
}
