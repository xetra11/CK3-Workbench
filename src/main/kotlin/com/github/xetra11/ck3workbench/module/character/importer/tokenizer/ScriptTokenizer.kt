package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/*
* Disassemble script into script language tokens for easier validation and generation
*/
class ScriptTokenizer {

    fun tokenize(file: File): List<Token> {
        return tokenize(file.readText())
    }

    fun tokenize(scriptContent: String): List<Token> {
        val preparedToken = scriptContent
            .trim()
            .split(" ")
            .filterNot { it.isBlank() }
            .map { it.replace("\n", "") }
            .map { Token(it, GrammarMatcher.TokenType.UNTYPED) }

        return resolveSymbols(preparedToken.toMutableList()) { withSymbols ->
            resolveIdentifier(withSymbols) { withIdentifiers ->
                resolveAttributeIdentifier(withIdentifiers) { withAttributeIdentifier ->
                    resolveAttributeValue(withAttributeIdentifier)
                }
            }
        }
    }

    private fun resolveSymbols(
        preparedToken: MutableList<Token>,
        nextProcessor: (preparedToken: MutableList<Token>) -> MutableList<Token>
    ): MutableList<Token> {
        val processed = preparedToken.map { token ->
            val type = when (token.value) {
                "{" -> GrammarMatcher.TokenType.BLOCK_START
                "}" -> GrammarMatcher.TokenType.BLOCK_END
                "=" -> GrammarMatcher.TokenType.ASSIGNMENT
                else -> GrammarMatcher.TokenType.UNTYPED
            }
            Token(token.value, type)
        }.toMutableList()

        return nextProcessor(processed)
    }

    private fun resolveIdentifier(
        preparedToken: MutableList<Token>,
        nextProcessor: (preparedToken: MutableList<Token>) -> MutableList<Token>
    ): MutableList<Token> {
        val indexOfAssignmentOperation = preparedToken.indexOfFirst { it.type == GrammarMatcher.TokenType.ASSIGNMENT }

        if (indexOfAssignmentOperation <= 0) {
            val message = "Assignment operation can not exist without left side value"
            throw ScriptTokenizerError(message)
        }
        if (indexOfAssignmentOperation + 1 >= preparedToken.size) {
            val message = "Assignment operation can not exist without right side value"
            throw ScriptTokenizerError(message)
        }

        val leftAssignmentToken = preparedToken[indexOfAssignmentOperation - 1]
        val rightAssignmentToken = preparedToken[indexOfAssignmentOperation + 1]

        if (leftAssignmentToken.type == GrammarMatcher.TokenType.UNTYPED) {
            preparedToken[indexOfAssignmentOperation - 1] = Token(leftAssignmentToken.value, GrammarMatcher.TokenType.OBJECT_ID)
        }

        if (rightAssignmentToken.type == GrammarMatcher.TokenType.UNTYPED) {
            preparedToken[indexOfAssignmentOperation + 1] = Token(rightAssignmentToken.value, GrammarMatcher.TokenType.ATTRIBUTE_VALUE)
        }

        return nextProcessor(preparedToken)
    }

    /*
    *
    * IDENTIFIER = {
    *   ATTRIBUTE_IDENTIFIER = ATTRIBUTE_VALUE
    * }    ^ to find
    *
    */
    private fun resolveAttributeIdentifier(
        preparedToken: MutableList<Token>,
        nextProcessor: (preparedToken: MutableList<Token>) -> MutableList<Token>
    ): MutableList<Token> {
        val sectionBlockStart = preparedToken.indexOfFirst { it.type == GrammarMatcher.TokenType.BLOCK_START }
        val sectionBlockEnd = preparedToken.indexOfFirst { it.type == GrammarMatcher.TokenType.BLOCK_END }

        // check for assignment operations within the section
        val assignmentIndices = mutableListOf<Int>()
        (sectionBlockStart..sectionBlockEnd).forEach { index ->
            val (value, type) = preparedToken[index]
            if (type == GrammarMatcher.TokenType.ASSIGNMENT) {
                assignmentIndices.add(index)
            }
        }

        assignmentIndices.forEach { indexOfAssignment ->
            val token = preparedToken[indexOfAssignment - 1]
            if (token.type == GrammarMatcher.TokenType.UNTYPED) {
                preparedToken[indexOfAssignment - 1] = Token(token.value, GrammarMatcher.TokenType.ATTRIBUTE_ID)
            }
        }
        return nextProcessor(preparedToken)
    }

    /*
    *
    * IDENTIFIER = {
    *   ATTRIBUTE_IDENTIFIER = ATTRIBUTE_VALUE
    * }                          ^ to find
    *
    */
    private fun resolveAttributeValue(preparedToken: MutableList<Token>): MutableList<Token> {
        val sectionBlockStart = preparedToken.indexOfFirst { it.type == GrammarMatcher.TokenType.BLOCK_START }
        val sectionBlockEnd = preparedToken.indexOfFirst { it.type == GrammarMatcher.TokenType.BLOCK_END }

        // check for assignment operations within the section
        val assignmentIndices = mutableListOf<Int>()
        (sectionBlockStart..sectionBlockEnd).forEach { index ->
            val (value, type) = preparedToken[index]
            if (type == GrammarMatcher.TokenType.ASSIGNMENT) {
                assignmentIndices.add(index)
            }
        }

        assignmentIndices.forEach { indexOfAssignment ->
            val token = preparedToken[indexOfAssignment + 1]
            if (token.type == GrammarMatcher.TokenType.UNTYPED) {
                preparedToken[indexOfAssignment + 1] = Token(token.value, GrammarMatcher.TokenType.ATTRIBUTE_VALUE)
            }
        }

        return preparedToken
    }

    data class Token(
        val value: String,
        val type: GrammarMatcher.TokenType
    )

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ScriptTokenizer::class.java)
    }
}

class ScriptTokenizerError(message: String) : Throwable(message)
