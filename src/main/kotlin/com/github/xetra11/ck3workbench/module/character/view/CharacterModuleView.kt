package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.xetra11.ck3workbench.module.character.Character
import com.github.xetra11.ck3workbench.module.character.ui.CharacterList
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Composable
fun CharacterModuleView(
    characterState: SnapshotStateList<Character>
) {
    Row {
        CharacterList(characterState)
    }
}

private fun LOG(): Logger {
    return LoggerFactory.getLogger("CharacterImportWindow")
}
