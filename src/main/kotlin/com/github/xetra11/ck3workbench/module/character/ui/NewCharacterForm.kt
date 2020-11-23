package com.github.xetra11.ck3workbench.module.character

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * @author Patrick C. Höfer
 */

@Composable
fun NewCharacterForm() {
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
