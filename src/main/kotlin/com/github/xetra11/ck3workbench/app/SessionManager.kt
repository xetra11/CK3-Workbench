package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CK3Character
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

/**
 * Deals with loading project files and assets to persist the state of the application
 */
class SessionManager(
    private val fileName: String = "project"
) {
    private val projectFile = File("$fileName.wbp")

    /**
     * Initializes the session by creating a fresh project file
     */
    fun initialize() {
        if (!projectFile.exists()) {
            projectFile.createNewFile()
            val project = Project()
            val projectData = Json.encodeToString(project)
            projectFile.writeText(projectData)
            NotificationsService.notify("Project ${project.name} has been initialized")
        } else {
            NotificationsService.notify("Loading project file...")
            val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())
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
        val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())
        projectFromFile.characters = StateManager.characters
        val updatedProjectData = Json.encodeToString(projectFromFile)
        projectFile.writeText(updatedProjectData)
        NotificationsService.notify("Character state saved to project file")
    }
}

@Serializable
data class Project(
    var name: String = "default",
    var characters: List<CK3Character> = listOf()
)
