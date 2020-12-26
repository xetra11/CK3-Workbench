package com.github.xetra11.ck3workbench.app

/**
 * Core logic to load the app state through session load and
 * project initialization
 */
class AppInitializer(
    private val sessionManager: SessionManager
) {

    fun initialize() {
        val loadedSession = sessionManager.load()
        SessionHolder.activeSession = loadedSession
        NotificationsService.notify("Session loaded as active session")
    }
}
