package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.module.character.AddCharacterDialog
import com.github.xetra11.ck3workbench.module.character.Character
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import com.github.xetra11.ck3workbench.module.character.ui.CharacterList
import com.github.xetra11.ck3workbench.module.character.window.CharacterScriptImport
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Composable
fun CharacterModuleView() {
    val characterState: SnapshotStateList<Character> = remember { mutableStateListOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
        )
    }

    Column(modifier = Modifier.border(1.dp, Color.Black).then(Modifier.fillMaxWidth())) {
        CharacterScriptImport(
            Modifier.padding(20.dp),
            characterState
        )
        AddCharacterDialog(Modifier.padding(20.dp))
        CharacterList(characterState)
    }
}

private fun LOG(): Logger {
    return LoggerFactory.getLogger("CharacterImportWindow")
}
