package com.github.xetra11.ck3workbench.module.character.importer

import androidx.compose.runtime.MutableState
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.StateManager
import com.github.xetra11.ck3workbench.app.validation.ScriptValidatorFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class CharacterScriptImporter {
    fun importCharactersScript(
        file: MutableState<File>
    ) {
        val scriptValidator = ScriptValidatorFactory.createScriptValidator()
        if (!scriptValidator.validate(file.value, "Character")) {
            NotificationsService.error("""Script to be imported is invalid""")
        } else {
            val characterScriptReader = CharacterScriptReader()
            val character = characterScriptReader.readCharacterScript(file.value.absoluteFile)
            val characterState = StateManager.characters
            character?.let {
                if (characterState.contains(it)) {
                    NotificationsService.error("""Character with name "${it.name}" already exists""")
                } else {
                    characterState.add(it)
                }
            }
        }
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(CharacterScriptImporter::class.java)
    }
}
