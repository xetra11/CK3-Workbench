package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Paths

/**
 * Core logic to load the app state through session load and
 * project initialization
 */
class AppInitializer(
    private val sessionManager: SessionManager
) {

    /**
     * Initialize session by loading session file and the associated last
     * used project.
     */
    fun initialize() {
        notify("Initialize session")
        val loadedSession = sessionManager.load()
        SessionHolder.activeSession = loadedSession

        val activeProjectLocation = loadedSession.activeProject.location
        val projectFile = Paths.get(activeProjectLocation).toFile()
        if (projectFile.exists()) {
            notify("Load session project")
            val projectData = projectFile.readText()
            val project = Json.decodeFromString<Project>(projectData)
            initializeState(project)
        } else {
            notify("No session project found")
        }
    }

    private fun initializeState(project: Project) {
        notify("Load characters module state")
        StateHolder.characters.addAll(project.state.characters)
    }
}
