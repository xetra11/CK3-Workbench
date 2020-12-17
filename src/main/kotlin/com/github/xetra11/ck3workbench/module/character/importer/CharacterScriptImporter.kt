package com.github.xetra11.ck3workbench.module.character.importer

import androidx.compose.runtime.MutableState
import com.github.xetra11.ck3workbench.module.character.app.NotificationsService
import com.github.xetra11.ck3workbench.module.character.app.StateManager
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class CharacterScriptImporter {
    fun importCharactersScript(
        file: MutableState<File>
    ) {
        val scriptValidator = ScriptValidatorFactory.createScriptValidator()
        if (!scriptValidator.validate(file.value, "Character")) {
            NotificationsService.notify("""Script to be imported is invalid""")
        } else {
            val characterScriptReader = CharacterScriptReader()
            val character = characterScriptReader.readCharacterScript(file.value.absoluteFile)
            val characterState = StateManager.characters
            character?.let {
                if (characterState.contains(it)) {
                    NotificationsService.notify("""Character with name "${it.name}" already exists""")
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
