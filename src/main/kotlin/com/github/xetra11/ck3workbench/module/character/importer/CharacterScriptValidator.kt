package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.ScriptValidator
import com.github.xetra11.ck3workbench.ScriptValidator.ValidationError
import com.github.xetra11.ck3workbench.ScriptValidator.ValidationResult

/**
 * Validats script inputs
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class CharacterScriptValidator: ScriptValidator {
    override fun validate(scriptToValidate: String): ValidationResult {
        if(!hasCompleteBrackets(scriptToValidate)) {
            return ValidationResult().error(ValidationError(
                reason = "Missing Brackets"
            ))
        }
        return ValidationResult()
    }

    /*
    * Checks if the given scripts has as many opening '{' as it has
    * closing backets '}'
    */
    private fun hasCompleteBrackets(scriptToValidate: String): Boolean {
        val sumOfBrackets = scriptToValidate.count { it == '{' || it == '}' }
        return sumOfBrackets % 2 == 0
    }
}
