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
        L_BRACE,
        R_BRACE,
        ASSIGNMENT,
        VALUE,
        UNTYPED
    }

    fun tokenize(file: File): List<Token> {
        return tokenize(file.readText())
    }

    fun tokenize(scriptContent: String): List<Token> {
        val preparedToken =  scriptContent
            .trim()
            .split(" ")
            .filterNot { it.isBlank() }
            .map { it.replace("\n", "") }
            .map { segment -> segment to resolveSymbols(segment)}
            .map { Token(it.first, it.second)}

        return resolveIdentifier(preparedToken.toMutableList())
    }

    private fun resolveIdentifier(preparedToken: MutableList<Token>): List<Token> {
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
            preparedToken[indexOfAssignmentOperation + 1] = Token(rightAssignmentToken.value, TokenType.VALUE)
        }

        return preparedToken
    }

    private fun resolveSymbols(string: String): TokenType {
        return when (string) {
            "{" -> TokenType.L_BRACE
            "}" -> TokenType.R_BRACE
            "=" -> TokenType.ASSIGNMENT
            else -> TokenType.UNTYPED
        }
    }

    data class Token(
        val value: String,
        val type: TokenType
    )

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ScriptTokenizer::class.java)
    }
}

class ScriptTokenizerError(message:String): Throwable(message) {

}
