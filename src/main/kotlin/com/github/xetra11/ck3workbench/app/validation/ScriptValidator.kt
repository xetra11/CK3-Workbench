package com.github.xetra11.ck3workbench.app.validation

import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarParser
import com.github.xetra11.ck3workbench.app.validation.grammar.GrammarValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/**
 * Validates given script files
 *
 * @author Patrick C. Höfer
 */
class ScriptValidator(
    private val grammarValidator: GrammarValidator,
    private val grammarParser: GrammarParser
) {
    fun validate(script: File, type: String): Boolean {
        val grammars = loadGrammar()
        val requiredGrammars = grammars.filter { it.definitionName.equals(type, ignoreCase = true) }
        if (requiredGrammars.isEmpty()) {
            LOG.warn("Could not find any grammars matching the type $type")
            LOG.info("Found grammar types are: ${grammars.map { it.definitionName }}")
            return false
        }
        return grammars.all { grammar ->
            !grammarValidator.rule(grammar, script.readLines()).hasError
        }
    }

    private fun loadGrammar(): List<GrammarParser.Grammar> {
        val grammarsDir = File("grammars")
        return if (grammarsDir.exists() && (grammarsDir.isDirectory)) {
            grammarsDir.walkTopDown()
                .filter { it.extension == "grm" }
                .map { grammarParser.process(it.readText(Charsets.UTF_8)) }
                .toList()
        } else {
            LOG.error("Path ${grammarsDir.absolutePath} does not exist or is not a directory")
            emptyList()
        }
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ScriptValidator::class.java)
    }
}
