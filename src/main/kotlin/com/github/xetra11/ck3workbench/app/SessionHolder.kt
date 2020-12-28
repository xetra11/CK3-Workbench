package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

/**
 * Holds the current session of the application
 */
object SessionHolder {
    var activeSession: MutableState<Session> = mutableStateOf(Session())
}
