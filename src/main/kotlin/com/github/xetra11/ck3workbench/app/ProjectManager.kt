package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CK3Character
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Manages project files to load/save different projects
 */
class ProjectManager {

    /**
     * Takes values to create a new project and saves it at given location
     *
     * @param name Name of the project
     * @param path Location where the project should be saved
     * @param description Description of the project
     * @return saved project
     */
    fun saveNewProject(name: String, path: Path, description: String): Project {
        val newProject = Project(name, path.toAbsolutePath().toString(), description)
        return saveProject(newProject)
    }

    /**
     * Save a given project file to the filesystem
     *
     * @param project The project to save
     * @return saved project
     */
    fun saveProject(project: Project): Project {
        val projectData = Json.encodeToString(project)
        Paths.get(project.location).toFile().writeText(projectData, Charsets.UTF_8)
        NotificationsService.notify("Save project to ${project.location}")
        return project
    }

    /**
     * Save the current project that is active. This will be determined by the [SessionHolder]'s
     * [Session] object where the current project resides in.
     */
    fun saveCurrentProject() {
        NotificationsService.notify("Save current project")
        SessionHolder.activeSession?.activeProject?.let { project ->
            project.state.characters = StateHolder.characters
            NotificationsService.notify("Save current project to ${project.location}")
            saveProject(project)
        } ?: run {
            NotificationsService.error("No active project could be found")
        }
    }

    /**
     * Load the given project by its location property from the filsystem
     *
     * @param project to load
     * @return the loaded project
     */
    fun loadProject(project: Project): Project {
        val projectFilePath = Paths.get(project.location)
        NotificationsService.notify("Load project from ${project.location}")
        val loadedProject = Json.decodeFromString<Project>(projectFilePath.toFile().readText(Charsets.UTF_8))
        SessionHolder.activeSession?.let { session ->
            session.activeProject = loadedProject
            session.recentProjects.add(loadedProject)
            NotificationsService.notify("Make loaded project active project in session")
        } ?: run {
            NotificationsService.error("There is no active session")
        }
        return loadedProject
    }
}

/**
 * Data Transfer Object of a project
 *
 * @param name The name of the project
 * @param location The filesystem location where this project's file should be saved to
 * @param description The description of the project
 * @param state The state the project contains (like drafts or work in progress character lists etc.)
 */
@Serializable
data class Project(
    var name: String = "default",
    var location: String = "",
    var description: String = "",
    var state: ProjectState = ProjectState()
)

/**
 * The state of a project
 *
 * @param characters The [CK3Character] characters list created in the character list menu
 */
@Serializable
data class ProjectState(
    var characters: List<CK3Character> = listOf()
)
