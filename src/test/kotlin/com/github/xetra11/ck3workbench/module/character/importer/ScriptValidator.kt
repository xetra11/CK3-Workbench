package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarMatcher
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser
import java.io.File

/**
 * Validates given script files
 *
 * @author Patrick C. Höfer
 */
class ScriptValidator(
    private val grammarMatcher: GrammarMatcher,
    private val grammarParser: GrammarParser
) {
    fun validate(script: File): Boolean {
        TODO("Not yet implemented")
    }
}
