package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.github.xetra11.ck3workbench.module.character.app.StateManager
import com.github.xetra11.ck3workbench.module.character.ui.CharacterList
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Composable
fun CharacterModuleView() {
    val characterState = StateManager.characters
    Row {
        CharacterList(characterState)
    }
}

private fun LOG(): Logger {
    return LoggerFactory.getLogger("CharacterImportWindow")
}
