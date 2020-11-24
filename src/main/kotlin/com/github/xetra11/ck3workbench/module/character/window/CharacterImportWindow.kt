package com.github.xetra11.ck3workbench.module.character.window

import androidx.compose.desktop.Window
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.lightColors
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.module.character.service.CharacterScriptReader
import com.github.xetra11.ck3workbench.module.character.view.CharacterModuleView
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Composable
fun CharacterImportWindow() {
    Window(title = "Character Import") {
        MaterialTheme(lightColors()) {
            Column(modifier = Modifier.border(1.dp, Color.Black).then(Modifier.fillMaxWidth())) {
                val characterFilePath = remember { mutableStateOf(TextFieldValue()) }
                TextField(
                    value = characterFilePath.value,
                    onValueChange = { characterFilePath.value = it}
                )
            }
        }
    }
}
