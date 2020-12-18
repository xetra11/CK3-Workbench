package com.github.xetra11.ck3workbench.app.view

import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.WindowManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.xetra11.ck3workbench.app.DialogManager
import com.github.xetra11.ck3workbench.module.character.view.CharacterCreateView

@Composable
fun DialogView() {
    when (DialogManager.activeDialog()) {
        DialogManager.Dialog.CREATE_CHARACTER -> CreateCharacterDialog()
        else -> {}
    }
}

@Composable
private fun CreateCharacterDialog() {
    Dialog(
        onDismissRequest = { DialogManager.closeDialog() }
    ) {
        Column(Modifier.fillMaxSize().border(2.dp, Color.Blue ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            CharacterCreateView()
/*
            Button(onClick = { AppManager.focusedWindow?.close() }) {
                Text("Close")
            }
*/
        }
    }
}
