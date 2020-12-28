package com.github.xetra11.ck3workbench.app

import java.nio.file.Path

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
    fun createProject(name: String, path: Path, description: String): Project {
        val newProject = Project(name, path.toAbsolutePath().toString(), description)
        return saveProject(newProject)
    }

    /**
     * Save a given project file to the filesystem
     *
     * @param project The project to save
     * @return saved project
     */
    private fun saveProject(project: Project): Project {
        NotificationsService.notify("Save project to ${project.location}")
        project.save()
        return project
    }

    /**
     * Save the current project that is active. This will be determined by the [SessionHolder]'s
     * [Session] object where the current project resides in.
     */
    fun saveCurrentProject() {
        NotificationsService.notify("Save current project")
        SessionHolder.activeSession?.activeProject?.let { sessionProject ->
            val loadedProject = sessionProject.toProject()
            NotificationsService.notify("Save current project to ${sessionProject.location}")
            updateState(loadedProject)
            saveProject(loadedProject)
        } ?: run {
            NotificationsService.error("No active project could be found")
        }
    }

    /**
     * Loads a given project state into the state holder
     * @param project to load the state from
     */
    fun load(project: Project) {
        StateHolder.characters.clear()
        StateHolder.characters.addAll(project.state.characters)
    }

    private fun updateState(loadedProject: Project) {
        loadedProject.state.characters = StateHolder.characters
    }
}
