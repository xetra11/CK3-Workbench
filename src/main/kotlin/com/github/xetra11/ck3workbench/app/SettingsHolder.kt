package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * Holds all the app wide settings state
 */
object SettingsHolder {
    var autosave by mutableStateOf(false)

    fun toAppSettings(): AppSettings {
        return AppSettings(
            autosave = autosave
        )
    }
}
