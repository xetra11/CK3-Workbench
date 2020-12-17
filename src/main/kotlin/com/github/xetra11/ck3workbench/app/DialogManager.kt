package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.mutableStateOf

object DialogManager {
    val currentDialog = mutableStateOf(Dialog.NO_DIALOG)

    enum class Dialog {
        CREATE_CHARACTER,
        NO_DIALOG
    }
}
