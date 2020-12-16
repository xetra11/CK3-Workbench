package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

object GrammarMappings {
    fun mapGrammar(token: String): GrammarValidator.TokenDefinition {
        return when (token) {
            "[ATTRIBUTE_ID]" -> GrammarValidator.TokenType.ATTRIBUTE_ID
            "[OBJECT_ID]" -> GrammarValidator.TokenType.OBJECT_ID
            "[BLOCK_START]" -> GrammarValidator.TokenType.BLOCK_START
            "[BLOCK_END]" -> GrammarValidator.TokenType.BLOCK_END
            "[ASSIGNMENT]" -> GrammarValidator.TokenType.ASSIGNMENT
            "[ATTRIBUTE_VALUE]" -> GrammarValidator.TokenType.ATTRIBUTE_VALUE

            "[ATTRIBUTE_ID?]" -> GrammarValidator.OptionalTokenType.ATTRIBUTE_ID
            "[OBJECT_ID?]" -> GrammarValidator.OptionalTokenType.OBJECT_ID
            "[BLOCK_START?]" -> GrammarValidator.OptionalTokenType.BLOCK_START
            "[BLOCK_END?]" -> GrammarValidator.OptionalTokenType.BLOCK_END
            "[ASSIGNMENT?]" -> GrammarValidator.OptionalTokenType.ASSIGNMENT
            "[ATTRIBUTE_VALUE?]" -> GrammarValidator.OptionalTokenType.ATTRIBUTE_VALUE

            else -> GrammarValidator.TokenType.UNTYPED
        }
    }
}
