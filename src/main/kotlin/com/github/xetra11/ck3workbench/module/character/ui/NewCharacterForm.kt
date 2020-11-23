package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue

/**
 * @author Patrick C. Höfer
 */

@Composable
fun NewCharacterForm() {
    Box(){
        Column(){
            Row(){
                Text("Name")
                val characterName = remember { mutableStateOf(TextFieldValue()) }
                TextField(
                    value = characterName.value,
                    onValueChange = { characterName.value = it },
                    placeholder = { Text("Enter name of character") }
                )
            }
        }
    }
}
