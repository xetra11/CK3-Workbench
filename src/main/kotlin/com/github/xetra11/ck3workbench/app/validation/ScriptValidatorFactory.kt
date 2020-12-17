package com.github.xetra11.ck3workbench.app.validation

import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarParser
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator

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
