package com.github.xetra11.ck3workbench.module.character.importer.tokenizer

import com.nhaarman.mockitokotlin2.mock

internal class GrammarValidatorTest {
    private val mockedGrammarParser: GrammarParser = mock()
    private val mockedGrammarMatcher: GrammarMatcher = mock()

    private val grammarValidator: GrammarValidator = GrammarValidator()
}
