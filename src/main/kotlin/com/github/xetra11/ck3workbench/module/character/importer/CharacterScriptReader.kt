package com.github.xetra11.ck3workbench.module.character.importer

import com.github.xetra11.ck3workbench.module.character.Character
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

/**
 * This service is meant to read in characters from the script files
 *
 * @author Patrick C. Höfer
 */
class CharacterScriptReader {
    fun readCharacterScript(file: File): Character? {
        return if (file.exists()) {
            val lines = file.readLines()
            val characterDefinition = extractCharacterDefinition(lines)
            removeScriptSections(characterDefinition)
            val characterAttributes = transformToAttributes(characterDefinition)
            Character.from(characterAttributes)
        } else {
            LOG.error("File ${file.absoluteFile} could not be found")
            null
        }
    }

    private fun transformToAttributes(characterDefinition: MutableList<String>): Map<String, String> {
        val characterAttributes = characterDefinition
            .map { character ->
                character.trim()
                    .trimIndent()
                    .replace(" ", "")
                    .replace("\"", "")
                    .split("=")
            }
            .associate { list -> list[0] to list[1] }
        return characterAttributes
    }

    private fun extractCharacterDefinition(lines: List<String>): MutableList<String> {
        //val amountOpenBrackets = lines.filter { it.contains("{") }.count()
        //val amountCloseBrackets = lines.filter { it.contains("}") }.count()

        val startLineNumber = lines.indexOfFirst { it.contains("{") }.plus(1)
        val endLineNumber = lines.indexOfLast { it.contains("}") }

        val characterDefinition = lines.subList(startLineNumber, endLineNumber)
            .filterNot { it.isBlank() }
            .toMutableList()
        return characterDefinition
    }

    private fun removeScriptSections(characterDefinition: MutableList<String>) {
        val innerScriptStart = characterDefinition.indexOfFirst { it.contains("{") }
        val innerScriptEnd = characterDefinition.indexOfLast { it.contains("}") }.plus(1)
        val innerScriptSection = characterDefinition.subList(innerScriptStart, innerScriptEnd)
        characterDefinition.removeAll(innerScriptSection)
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(CharacterScriptReader::class.java)
    }
}
