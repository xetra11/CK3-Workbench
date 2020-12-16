package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarParser
import com.github.xetra11.ck3workbench.module.character.importer.tokenizer.GrammarValidator

/**
 * Creates instances of [ScriptValidator]
 */
object ScriptValidatorFactory {
    /**
     * Create a [ScriptValidator] instance
     * @return instance of [ScriptValidator]
     */
    fun createScriptValidator(): ScriptValidator {
        val grammarValidator = GrammarValidator()
        val grammarParser = GrammarParser()
        return ScriptValidator(grammarValidator, grammarParser)
    }
}
