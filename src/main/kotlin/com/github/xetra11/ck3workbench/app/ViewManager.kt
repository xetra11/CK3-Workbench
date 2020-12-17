package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.mutableStateOf

object ViewManager {
    val currentView = mutableStateOf(View.CHARACTER_VIEW)

    enum class View {
        CHARACTER_VIEW,
        OTHER_VIEW
    }
}
