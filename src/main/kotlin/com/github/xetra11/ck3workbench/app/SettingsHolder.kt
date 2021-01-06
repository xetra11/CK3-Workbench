package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.github.xetra11.ck3workbench.module.character.CK3Character

/**
 * Holds all the app wide settings state
 */
object SettingsHolder {
    var autosave by mutableStateOf(false)
}
