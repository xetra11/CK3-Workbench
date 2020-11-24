package com.github.xetra11.ck3workbench.module.character.service

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
    fun readCharacterScript(): Character? {
        val file = File("src/test/resources/fixtures/character/test_character.txt")
        val lines = file.readLines()

        val amountOpenBrackets = lines.filter { line -> line.contains("{") }.count()
        val amountCloseBrackets = lines.filter { line -> line.contains("}") }.count()

        val startLineNumber = lines.indexOfFirst { line -> line.contains("{") }.plus(1)
        val endLineNumber = lines.indexOfLast { line -> line.contains("}") }.plus(1)

        val characterDefinition = lines.subList(startLineNumber, endLineNumber)
            .filterNot { it.isBlank() }

        val preparedCharacterDefintion = characterDefinition.map {
            it.trim()
                .trimIndent()
                .replace(" ", "")
                .replace("\"", "")
                .split("=")
        }

        return null
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(CharacterScriptReader::class.java)
    }
}
