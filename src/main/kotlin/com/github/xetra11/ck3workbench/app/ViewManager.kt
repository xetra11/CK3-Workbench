package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.mutableStateOf

object ViewManager {
    val currentView = mutableStateOf(View.ENTRY_VIEW)

    enum class View {
        CHARACTER_VIEW,
        CHARACTER_CREATE_VIEW,
        ENTRY_VIEW,
        DYNASTY_VIEW
    }
}
