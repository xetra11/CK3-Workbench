package com.github.xetra11.ck3workbench.app

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Core logic to load the app state through session load and
 * project initialization
 */
class AppInitializer(
    private val sessionManager: SessionManager
) {

    fun initialize() {
        val sessionFile = File("session.wbs")
        NotificationsService.notify("Load session from file ${sessionFile.absolutePath}")
        val sessionData = sessionFile.readText(Charsets.UTF_8)
        val sessionFromFile = Json.decodeFromString<Session>(sessionData)

        SessionHolder.activeSession = sessionFromFile
        NotificationsService.notify("Session loaded as active session")
    }
}
