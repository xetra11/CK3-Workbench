package com.github.xetra11.ck3workbench.app.view

import androidx.compose.desktop.AppManager
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.xetra11.ck3workbench.app.DialogManager
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.StateManager
import com.github.xetra11.ck3workbench.app.styles.WorkbenchButtons.BasicButton
import com.github.xetra11.ck3workbench.app.styles.WorkbenchTexts.BasicButtonText
import com.github.xetra11.ck3workbench.module.character.exporter.CharacterScriptExporter
import com.github.xetra11.ck3workbench.module.character.view.CharacterCreateView

@Composable
fun DialogView() {
    when (DialogManager.activeDialog()) {
        DialogManager.Dialog.CREATE_CHARACTER -> CreateCharacterDialog()
        DialogManager.Dialog.CHARACTER_EXPORT -> ExportCharacterDialog()
        else -> {
        }
    }
}

@Composable
private fun CreateCharacterDialog() {
    Dialog(
        onDismissRequest = { DialogManager.closeDialog() }
    ) {
        Column(
            Modifier.fillMaxSize().border(2.dp, Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            CharacterCreateView()
/*
            Button(onClick = { AppManager.focusedWindow?.close() }) {
                Text("Close")
            }
*/
        }
    }
}

@Composable
private fun ExportCharacterDialog() {
    AlertDialog(
        title = { Text("Character Export") },
        text = { Text("Do you want to export all ${StateManager.characters.size} character entries?") },
        onDismissRequest = { DialogManager.closeDialog() },
        confirmButton = {
            BasicButton(
                onClick = {
                    val characterScriptExporter = CharacterScriptExporter()
                    characterScriptExporter.export()
                    NotificationsService.notify("""Characters have been exported to "character.txt"""")
                }) { BasicButtonText("Export") }
        },
        dismissButton = {
            BasicButton(onClick = { AppManager.focusedWindow?.close() }) { BasicButtonText("Cancel") }
        }
    )
}
