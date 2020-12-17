package com.github.xetra11.ck3workbench.module.character.importer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.xetra11.ck3workbench.module.character.Character
import com.github.xetra11.ck3workbench.module.character.app.NotificationsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File

class CharacterScriptImporter {
    fun importCharactersScript(
        file: MutableState<File>,
        characterState: SnapshotStateList<Character>
    ) {
        val scriptValidator = ScriptValidatorFactory.createScriptValidator()
        if (!scriptValidator.validate(file.value, "Character")) {
            NotificationsService.notify("""Script to be imported is invalid""")
        } else {
            val characterScriptReader = CharacterScriptReader()
            val character = characterScriptReader.readCharacterScript(file.value.absoluteFile)
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
