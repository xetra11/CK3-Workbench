package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.module.character.AddCharacterDialog
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import com.github.xetra11.ck3workbench.module.character.ui.CharacterList
import com.github.xetra11.ck3workbench.module.character.window.CharacterScriptImport
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Composable
fun CharacterModuleView() {
    Column(modifier = Modifier.border(1.dp, Color.Black).then(Modifier.fillMaxWidth())) {
        CharacterScriptImport(Modifier.padding(20.dp))
        AddCharacterDialog(Modifier.padding(20.dp))
        CharacterList(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER,
            )
        )
    }
}

private fun LOG(): Logger {
    return LoggerFactory.getLogger("CharacterImportWindow")
}
