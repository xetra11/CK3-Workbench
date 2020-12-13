package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

object GrammarMappings {
    fun mapGrammar(token: String): GrammarMatcher.TokenDefinition {
        return when (token) {
            "[ATTRIBUTE_ID]" -> GrammarMatcher.TokenType.ATTRIBUTE_ID
            "[OBJECT_ID]" -> GrammarMatcher.TokenType.OBJECT_ID
            "[BLOCK_START]" -> GrammarMatcher.TokenType.BLOCK_START
            "[BLOCK_END]" -> GrammarMatcher.TokenType.BLOCK_END
            "[ASSIGNMENT]" -> GrammarMatcher.TokenType.ASSIGNMENT
            "[ATTRIBUTE_VALUE]" -> GrammarMatcher.TokenType.ATTRIBUTE_VALUE

            "[ATTRIBUTE_ID?]" -> GrammarMatcher.OptionalTokenType.ATTRIBUTE_ID
            "[OBJECT_ID?]" -> GrammarMatcher.OptionalTokenType.OBJECT_ID
            "[BLOCK_START?]" -> GrammarMatcher.OptionalTokenType.BLOCK_START
            "[BLOCK_END?]" -> GrammarMatcher.OptionalTokenType.BLOCK_END
            "[ASSIGNMENT?]" -> GrammarMatcher.OptionalTokenType.ASSIGNMENT
            "[ATTRIBUTE_VALUE?]" -> GrammarMatcher.OptionalTokenType.ATTRIBUTE_VALUE

            else -> GrammarMatcher.TokenType.UNTYPED
        }
    }
}
