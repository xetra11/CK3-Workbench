package com.github.xetra11.ck3workbench.module.character.window

import androidx.compose.desktop.AppWindowAmbient
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.module.character.Character
import com.github.xetra11.ck3workbench.module.character.importer.CharacterScriptReader
import java.awt.FileDialog
import java.awt.FileDialog.LOAD
import java.io.File

@Composable
fun CharacterScriptImportControls(
    characterState: SnapshotStateList<Character>
) {
    val modifier = Modifier.padding(10.dp)
    val window = AppWindowAmbient.current!!.window
    val file = remember { mutableStateOf(File("")) }

    Column(modifier = modifier
        .fillMaxWidth()
        .border(2.dp, Color.Black))  {
        Row {
            Text(
                modifier = modifier, text =
                if (file.value.exists()) "Script to import: ${file.value.absolutePath}"
                else "No script selected"
            )
        }
        Row {
            Button(
                modifier = modifier.preferredSize(70.dp, 25.dp),
                onClick = {
                    val fileDialog = FileDialog(window)
                    fileDialog.mode = LOAD
                    fileDialog.isVisible = true
                    file.value = File(fileDialog.directory + fileDialog.file)
                }
            ) {
                Text(
                    fontSize = TextUnit.Companion.Sp(9),
                    text = "Select"
                )
            }

            Button(
                modifier = modifier,
                onClick = {
                    val characterScriptReader = CharacterScriptReader()
                    val character = characterScriptReader.readCharacterScript(file.value.absoluteFile)
                    character?.let { characterState.add(it) }
                }
            ) {
                Text("Import")
            }
        }
    }
}
