package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Paths

/**
 * Manages the creation of the workbench [Session] and loads the latest [Session] state
 */
class SessionManager {
    private val filePath = Paths.get("session.wbs").toAbsolutePath()
    private val sessionFile = filePath.toFile()

    /**
     * Load the [Session] if it exists otherwise it will create a new initial [Session] file
     * @return The new or loaded [Session]
     */
    fun load(): Session {
        return if (!sessionFile.exists()) {
            notify("Create fresh session")
            sessionFile.createNewFile()
            val session = Session()
            val sessionData = Json.encodeToString(session)
            sessionFile.writeText(sessionData)
            session
        } else {
            notify("Load session from file ${sessionFile.absolutePath}")
            val sessionFromFile = Json.decodeFromString<Session>(sessionFile.readText())
            sessionFromFile
        }
    }

    /**
     * This call should be hooked to the application onExit event.
     * It runs through state and other memory saved data to save them
     * in the session file
     */
    fun exit() {
        notify("Preparing exit for session")
        SessionHolder.activeSession?.let { activeSession ->
            val updatedProjectData = Json.encodeToString(activeSession)
            sessionFile.writeText(updatedProjectData)
            notify("Session saved")
        }
    }
}

@Serializable
/**
 * The workbench work [Session]
 * @param activeProject The active/used project of the session
 * @param recentProjects All projects recently used
 */
data class Session(
    var activeProject: SessionProject = SessionProject(),
    var recentProjects: MutableList<Project> = mutableListOf()
)

@Serializable
/**
 * Represents the project of a [Session].
 * In fact it simply holds the location of the project to be loaded
 * @param location The file location of the project
 */
data class SessionProject(
    var location: String = ""
)
