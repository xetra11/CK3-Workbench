package com.github.xetra11.ck3workbench.app.dialog

import androidx.compose.runtime.mutableStateOf

object DialogManager {
    private val currentDialog = mutableStateOf(Dialog.NO_DIALOG)

    /**
     * Change active Dialog
     * @param newView that should be used
     */
    fun openDialog(dialog: Dialog) {
        currentDialog.value = dialog
    }

    /**
     * The active dialog
     * @return the main view that is currently active
     */
    fun activeDialog(): Dialog {
        return currentDialog.value
    }

    /**
     * Close the dialog
     */
    fun closeDialog() {
        currentDialog.value = Dialog.NO_DIALOG
    }

    enum class Dialog {
        CREATE_PROJECT,
        CREATE_CHARACTER,
        CHARACTER_EXPORT,
        NO_DIALOG,
        SAVE_BEFORE_EXIT
    }
}
