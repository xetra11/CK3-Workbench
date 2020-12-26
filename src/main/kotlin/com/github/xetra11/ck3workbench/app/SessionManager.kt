package com.github.xetra11.ck3workbench.app

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Paths

/**
 * Deals with loading project files and assets to persist the state of the application
 */
class SessionManager {
    private val filePath = Paths.get("session.wbs").toAbsolutePath()
    private val sessionFile = filePath.toFile()

    /**
     * Initializes the session by creating a fresh session file
     */
    fun load(): Session {
        return if (!sessionFile.exists()) {
            sessionFile.createNewFile()
            val session = Session()
            val sessionData = Json.encodeToString(session)
            sessionFile.writeText(sessionData)
            NotificationsService.notify("Session has been initialized")
            session
        } else {
            NotificationsService.notify("Load session from file ${sessionFile.absolutePath}")
            val sessionFromFile = Json.decodeFromString<Session>(sessionFile.readText())
            NotificationsService.notify("Session loaded and set in SessionHolder")
            sessionFromFile
        }
    }

    /**
     * This call should be hooked to the application onExit event.
     * It runs through state and other memory saved data to save them
     * in the session file
     */
    fun exit() {
        NotificationsService.notify("Preparing exit for session")
        SessionHolder.activeSession?.let { activeSession ->
            val updatedProjectData = Json.encodeToString(activeSession)
            sessionFile.writeText(updatedProjectData)
            NotificationsService.notify("Session saved")
        }
    }
}

@Serializable
data class Session(
    var activeProject: Project? = null,
    var recentProjects: MutableList<Project> = mutableListOf()
)
