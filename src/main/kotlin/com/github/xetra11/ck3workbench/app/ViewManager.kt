package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.mutableStateOf

object ViewManager {
    private val currentView = mutableStateOf(View.ENTRY_VIEW)

    /**
     * Change main layout to another view
     * @param newView that should be used
     */
    fun changeView(newView: View) {
        currentView.value = newView
    }

    /**
     * The active main view
     * @return the main view that is currently active
     */
    fun currentMainView(): View {
        return currentView.value
    }

    enum class View {
        CHARACTER_VIEW,
        CHARACTER_CREATE_VIEW,
        ENTRY_VIEW,
        DYNASTY_VIEW
    }
}
