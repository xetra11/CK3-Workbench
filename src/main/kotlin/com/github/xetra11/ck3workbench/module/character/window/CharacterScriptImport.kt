package com.github.xetra11.ck3workbench.module.character.window

import androidx.compose.desktop.AppWindowAmbient
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.github.xetra11.ck3workbench.module.character.service.CharacterScriptReader
import java.awt.FileDialog
import java.awt.FileDialog.LOAD
import java.io.File

@Composable
fun CharacterScriptImport(
    modifier: Modifier = Modifier
) {
    val window = AppWindowAmbient.current!!.window
    val file = remember { mutableStateOf(File("")) }

    Row {
        Text("Script to import:")
        Text(file.value.absolutePath)
    }
    Row {
        Button(
            modifier = modifier,
            onClick = {
                val fileDialog = FileDialog(window)
                fileDialog.mode = LOAD
                fileDialog.isVisible = true
                file.value = File(fileDialog.directory + fileDialog.file)
            }
        ) {
            Text("Select Script")
        }

        Button(
            modifier = modifier,
            onClick = {
                val characterScriptReader = CharacterScriptReader()
                val character = characterScriptReader.readCharacterScript(file.value.absoluteFile)
            }
        ) {
            Text("Import")
        }
    }
}
