package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.ScriptValidator
import com.github.xetra11.ck3workbench.ScriptValidator.ValidationError
import com.github.xetra11.ck3workbench.ScriptValidator.ValidationResult

/**
 * Validats script inputs
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class CharacterScriptValidator : ScriptValidator {

    override fun validate(scriptToValidate: String): ValidationResult {
        val validation = ValidationResult()
        hasCompleteBrackets(scriptToValidate).let {
            if (it.hasErrors) {
                validation.errors.addAll(it.errors)
            }
        }
        hasHistoricalId(scriptToValidate).let {
            if (it.hasErrors) {
                validation.errors.addAll(it.errors)
            }
        }
        return validation
    }

    /*
    * Checks if the given script has as many opening '{' as it has closing brackets '}'
    */
    private fun hasCompleteBrackets(scriptToValidate: String): ValidationResult {
        val sumOfBrackets = scriptToValidate.count { it == '{' || it == '}' }
        return if (sumOfBrackets % 2 == 0) ValidationResult()
        else ValidationResult().error(ValidationError(reason = "missing brackets"))
    }

    /*
    * Checks if the given scripts has an historical id
    */
    private fun hasHistoricalId(scriptToValidate: String): ValidationResult {
        var valid: Boolean
        val sumOfBrackets = scriptToValidate.count { it == '{' || it == '}' }
        val sumOfAssignments = scriptToValidate.count { it == '=' }
        // At least as many complete blocks as there are assignments
        valid = (sumOfBrackets / 2) <= sumOfAssignments

        val split = scriptToValidate.split('=')
        val leftAssignment = split[0]
        valid = valid && leftAssignment.isNotBlank()

        return if (valid) {
            ValidationResult()
        } else ValidationResult().error(ValidationError(reason = "missing historical id"))
    }
}
