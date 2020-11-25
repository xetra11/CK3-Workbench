package com.github.xetra11.ck3workbench.module.character.importer

/**
 *
 * @author Patrick C. Höfer aka "xetra11"
 */
class CharacterScriptValidator {
    fun validate(scriptToValidate: String): Boolean {
        return hasCompleteBrackets(scriptToValidate)
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
