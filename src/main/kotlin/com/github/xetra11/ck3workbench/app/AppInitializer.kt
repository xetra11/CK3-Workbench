package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import com.github.xetra11.ck3workbench.app.project.Project
import com.github.xetra11.ck3workbench.app.project.ProjectManager
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Paths

/**
 * Core logic to load the app state through session load and
 * project initialization
 */
class AppInitializer(
    private val sessionManager: SessionManager,
    private val projectManager: ProjectManager
) {

    /**
     * Initialize session by loading session file and the associated last
     * used project.
     */
    fun initialize() {
        initializeSession()
        initializeSettings()
    }

    private fun initializeSettings() {
        SettingsHolder.init()
        SettingsHolder.loadToHolder()
    }

    private fun initializeSession() {
        notify("Initialize session")
        val loadedSession = sessionManager.load()
        SessionHolder.activeSession.value = loadedSession

        loadedSession.activeProject?.location?.let { activeProjectLocation ->
            val projectFile = Paths.get(activeProjectLocation).toFile()
            if (projectFile.exists()) {
                notify("Load session project")
                val projectData = projectFile.readText()
                val project = Json.decodeFromString<Project>(projectData)
                initializeState(project)
            } else {
                notify("No session project found")
                notify("Creating default project")
                val project = projectManager.createProject(
                    "Default",
                    Paths.get("default.wbp"),
                    "This is the default project"
                )
                SessionHolder.activeSession.value.activeProject = SessionProject(project.location)
            }
        }
    }

    private fun initializeState(project: Project) {
        notify("Load characters module state")
        StateHolder.characters.addAll(project.state.characters)
    }
}
