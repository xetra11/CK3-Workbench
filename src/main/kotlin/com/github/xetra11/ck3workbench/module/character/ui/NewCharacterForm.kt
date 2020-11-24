package com.github.xetra11.ck3workbench.module.character

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

/**
 * @author Patrick C. Höfer
 */

@Composable
fun AddCharacterDialog(
    modifier: Modifier = Modifier
) {
    val dialogState = remember { mutableStateOf(false) }
    Button(
        modifier = modifier,
        onClick = { dialogState.value = true }
    ){
        Text("Add Character")
    }
    if (dialogState.value) {
        CharacterDialogPopup(dialogState)
    }
}

@Composable
private fun CharacterDialogPopup(
    dialogState: MutableState<Boolean>
) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { dialogState.value = false }
    ) {
        val characterFilePath = remember { mutableStateOf(TextFieldValue()) }
        Box(Modifier.border(1.dp, Color.Black)){
            Column(){
                Row(){
                    val characterName = remember { mutableStateOf(TextFieldValue()) }
                    TextField(
                        label =  { Text("Name") },
                        value = characterName.value,
                        onValueChange = { characterName.value = it },
                        placeholder = { Text("Enter name of character") }
                    )
                }
            }
        }
    }
}
