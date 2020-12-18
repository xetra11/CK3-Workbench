package com.github.xetra11.ck3workbench.app.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.github.xetra11.ck3workbench.app.DialogManager
import com.github.xetra11.ck3workbench.module.character.view.CharacterCreateView

@Composable
fun DialogView() {
    when (DialogManager.currentDialog.value) {
        DialogManager.Dialog.CREATE_CHARACTER -> CreateCharacterDialog()
        DialogManager.Dialog.NO_DIALOG -> {}
    }
}

@Composable
private fun CreateCharacterDialog() {
    Dialog(
        onDismissRequest = {
            DialogManager.currentDialog.value = DialogManager.Dialog.NO_DIALOG
        }
    ) {
        CharacterCreateView()
    }
}
