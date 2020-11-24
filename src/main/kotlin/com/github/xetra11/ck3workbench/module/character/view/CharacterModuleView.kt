package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import com.github.xetra11.ck3workbench.module.character.NewCharacterForm
import com.github.xetra11.ck3workbench.module.character.ui.CharacterList
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Composable
fun CharacterModuleView() {
    val dialogState = remember { mutableStateOf(false) }
    Button(
        onClick = { dialogState.value = true }
    ){
        Text("Import Characters")
    }
    if (dialogState.value) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { dialogState.value = false }
            ) {
                val characterFilePath = remember { mutableStateOf(TextFieldValue()) }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.background(Color.Blue).then(
                        Modifier.size(200.dp, 200.dp)
                    )
                ) {
                    Button(
                        onClick = {}
                    ) {
                        Text("Import")
                    }
                }
            }
        }
    NewCharacterForm()
    CharacterList(
        listOf(
            CharacterTemplate.DEFAULT_CHARACTER,
            CharacterTemplate.DEFAULT_CHARACTER,
            CharacterTemplate.DEFAULT_CHARACTER,
        )
    )
}

private fun LOG(): Logger {
    return LoggerFactory.getLogger("CharacterImportWindow")
}
