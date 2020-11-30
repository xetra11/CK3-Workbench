package com.github.xetra11.ck3workbench.module.character.importer

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
        return scriptContent
            .trim()
            .split(" ")
            .filterNot { it.isBlank() }
            .map { it.replace("\n", "") }
            .map { segment -> segment to resolveSymbols(segment)}
            .map { Token(it.first, it.second)}
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
}
