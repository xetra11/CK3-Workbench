package com.github.xetra11.ck3workbench.app.project

import ProjectFileFilter
import androidx.compose.desktop.AppManager
import androidx.compose.desktop.ComposeWindow
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import com.github.xetra11.ck3workbench.app.SessionHolder
import com.github.xetra11.ck3workbench.app.SessionManager
import com.github.xetra11.ck3workbench.app.StateHolder
import com.github.xetra11.ck3workbench.app.loadProject
import java.nio.file.Path
import javax.swing.JFileChooser

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
    fun saveProject(project: Project): Project {
        notify("Save project to ${project.location}")
        updateState(project)
        project.save()
        return project
    }

    /**
     * Save the current project that is active. This will be determined by the [SessionHolder]'s
     * [Session] object where the current project resides in.
     */
    fun saveCurrentProject() {
        notify("Save current project")
        SessionHolder.activeSession.value.activeProject?.let { sessionProject ->
            if (sessionProject.location.isBlank()) {
                saveProjectDialog(AppManager.focusedWindow!!.window)
            } else {
                val loadedProject = sessionProject.loadProject()
                notify("Save current project to ${sessionProject.location}")
                updateState(loadedProject)
                saveProject(loadedProject)
            }
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
        notify("Project ${project.name} loaded")
    }

    private fun saveProjectDialog(window: ComposeWindow) {
        val fileChooser = JFileChooser()
        fileChooser.addChoosableFileFilter(ProjectFileFilter())

        when (fileChooser.showSaveDialog(window)) {
            JFileChooser.APPROVE_OPTION -> {
                notify("Save project to file")
                val projectManager = ProjectManager()
                val sessionManager = SessionManager()
                val projectFile = fileChooser.selectedFile
                val projectToSave = Project()

                projectToSave.location = projectFile.absolutePath + ".wbp"
                projectManager.saveProject(projectToSave)
                sessionManager.activateProject(projectToSave)
            }
            JFileChooser.CANCEL_OPTION -> {
                notify("Cancel saving project before exit")
                notify("Project has not been saved before exit")
            }
        }
    }

    private fun updateState(project: Project) {
        project.state.characters = StateHolder.characters
    }
}
