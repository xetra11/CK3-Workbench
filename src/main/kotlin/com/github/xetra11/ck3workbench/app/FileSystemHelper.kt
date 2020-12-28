package com.github.xetra11.ck3workbench.app

import ProjectFileFilter
import androidx.compose.desktop.ComposeWindow
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.awt.FileDialog
import java.io.File
import javax.swing.JFileChooser

class FileSystemHelper {
    fun saveProjectAs(window: ComposeWindow) {
        val fileChooser = JFileChooser()
        fileChooser.addChoosableFileFilter(ProjectFileFilter())

        when (fileChooser.showSaveDialog(window)) {
            JFileChooser.APPROVE_OPTION -> {
                NotificationsService.notify("Save project to file")
                val projectManager = ProjectManager()
                val sessionManager = SessionManager()
                val projectFile = fileChooser.selectedFile
                val currentProject = SessionHolder.activeSession.value.activeProject?.loadProject()

                currentProject?.let { project ->
                    project.location = projectFile.absolutePath + ".wbp"
                    projectManager.saveProject(currentProject)
                    sessionManager.activateProject(project)
                } ?: run {
                    NotificationsService.error("No current project was available")
                }
            }
            JFileChooser.CANCEL_OPTION -> {
                // warn("Cancel project file opening")
            }
        }
    }

    fun loadProjectFile(window: ComposeWindow) {
        val fileChooser = JFileChooser()
        fileChooser.addChoosableFileFilter(ProjectFileFilter())

        when (fileChooser.showOpenDialog(window)) {
            JFileChooser.APPROVE_OPTION -> {
                NotificationsService.notify("Load project from file")
                val projectManager = ProjectManager()
                val sessionManager = SessionManager()
                val projectFile = fileChooser.selectedFile
                val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())
                projectManager.load(projectFromFile)
                sessionManager.activateProject(projectFromFile)
            }
            JFileChooser.CANCEL_OPTION -> {
                // warn("Cancel project file opening")
            }
        }
    }

    fun openScriptFile(window: ComposeWindow): MutableState<File> {
        val file = mutableStateOf(File(""))

        val fileDialog = FileDialog(window)
        fileDialog.mode = FileDialog.LOAD
        fileDialog.isVisible = true
        file.value = File(fileDialog.directory + fileDialog.file)
        return file
    }
}
