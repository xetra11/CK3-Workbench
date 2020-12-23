package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CK3Character
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Paths

/**
 * Deals with loading project files and assets to persist the state of the application
 */
class SessionManager {
    private val fileName: String = "session"
    private val filePath = Paths.get("$fileName.wbp").toAbsolutePath()
    private val sessionFile = filePath.toFile()

    /**
     * Initializes the session by creating a fresh session file
     */
    fun initialize() {
        if (!sessionFile.exists()) {
            sessionFile.createNewFile()
            val project = Session()
            val projectData = Json.encodeToString(project)
            sessionFile.writeText(projectData)
            NotificationsService.notify("Project ${project.name} has been initialized")
        } else {
            NotificationsService.notify("Loading project file...")
            val projectFromFile = Json.decodeFromString<Session>(sessionFile.readText())
            StateManager.characters.addAll(projectFromFile.characters)
            NotificationsService.notify("Project file loaded")
        }
    }

    /**
     * This call should be hooked to the application onExit event.
     * It runs through state and other memory saved data to save them
     * in the project file
     */
    fun onExit() {
        val projectFromFile = Json.decodeFromString<Session>(sessionFile.readText())
        projectFromFile.characters = StateManager.characters
        val updatedProjectData = Json.encodeToString(projectFromFile)
        sessionFile.writeText(updatedProjectData)
        NotificationsService.notify("Character state saved to project file")
    }
}

@Serializable
data class Session(
    var name: String = "default",
    var characters: List<CK3Character> = listOf()
)
