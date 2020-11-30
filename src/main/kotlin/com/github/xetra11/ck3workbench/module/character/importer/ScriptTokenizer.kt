package com.github.xetra11.ck3workbench.module.character.importer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/*
* Disassemble script into script language tokens for easier validation and generation
*/
class ScriptTokenizer {
    enum class TokenType {
        IDENTIFIER,
        ATTRIBUTE_IDENTIFIER,
        ATTRIBUTE_VALUE,
        L_BRACE,
        R_BRACE,
        ASSIGNMENT,
        UNTYPED
    }

    fun tokenize(file: File): List<Token> {
        return tokenize(file.readText())
    }

    fun tokenize(scriptContent: String): List<Token> {
        val preparedToken = scriptContent
            .trim()
            .split(" ")
            .filterNot { it.isBlank() }
            .map { it.replace("\n", "") }
            .map { Token(it, TokenType.UNTYPED) }

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
            val type  = when (token.value) {
                "{" -> TokenType.L_BRACE
                "}" -> TokenType.R_BRACE
                "=" -> TokenType.ASSIGNMENT
                else -> TokenType.UNTYPED
            }
            Token(token.value, type)
        }.toMutableList()

        return nextProcessor(processed)
    }

    private fun resolveIdentifier(
        preparedToken: MutableList<Token>,
        nextProcessor: (preparedToken: MutableList<Token>) -> MutableList<Token>
    ): MutableList<Token> {
        val indexOfAssignmentOperation = preparedToken.indexOfFirst { it.type == TokenType.ASSIGNMENT }

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

        if (leftAssignmentToken.type == TokenType.UNTYPED) {
            preparedToken[indexOfAssignmentOperation - 1] = Token(leftAssignmentToken.value, TokenType.IDENTIFIER)
        }

        if (rightAssignmentToken.type == TokenType.UNTYPED) {
            preparedToken[indexOfAssignmentOperation + 1] = Token(rightAssignmentToken.value, TokenType.ATTRIBUTE_VALUE)
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
        val sectionEntry = preparedToken.indexOfFirst { it.type == TokenType.IDENTIFIER }
        val sectionBlockStart = preparedToken.indexOfFirst { it.type == TokenType.L_BRACE }
        val sectionBlockEnd = preparedToken.indexOfFirst { it.type == TokenType.R_BRACE }

        // check for assignment operations within the section
        val assignmentIndices = mutableListOf<Int>()
        (sectionBlockStart..sectionBlockEnd).forEach { index ->
            val (value, type) = preparedToken[index]
            if (type == TokenType.ASSIGNMENT) {
                assignmentIndices.add(index)
            }
        }

        assignmentIndices.forEach { indexOfAssignment ->
            val token = preparedToken[indexOfAssignment - 1]
            if (token.type == TokenType.UNTYPED) {
                preparedToken[indexOfAssignment - 1] = Token(token.value, TokenType.ATTRIBUTE_IDENTIFIER)
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
        val sectionEntry = preparedToken.indexOfFirst { it.type == TokenType.IDENTIFIER }
        val sectionBlockStart = preparedToken.indexOfFirst { it.type == TokenType.L_BRACE }
        val sectionBlockEnd = preparedToken.indexOfFirst { it.type == TokenType.R_BRACE }

        // check for assignment operations within the section
        val assignmentIndices = mutableListOf<Int>()
        (sectionBlockStart..sectionBlockEnd).forEach { index ->
            val (value, type) = preparedToken[index]
            if (type == TokenType.ASSIGNMENT) {
                assignmentIndices.add(index)
            }
        }

        assignmentIndices.forEach { indexOfAssignment ->
            val token = preparedToken[indexOfAssignment + 1]
            if (token.type == TokenType.UNTYPED) {
                preparedToken[indexOfAssignment + 1] = Token(token.value, TokenType.ATTRIBUTE_VALUE)
            }
        }

        return preparedToken
    }

    data class Token(
        val value: String,
        val type: TokenType
    )

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ScriptTokenizer::class.java)
    }
}

class ScriptTokenizerError(message: String) : Throwable(message)
