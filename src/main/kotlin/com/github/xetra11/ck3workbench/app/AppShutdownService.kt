package com.github.xetra11.ck3workbench.app

import ProjectFileFilter
import androidx.compose.desktop.AppManager
import androidx.compose.desktop.ComposeWindow
import com.github.xetra11.ck3workbench.app.NotificationsService.error
import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import com.github.xetra11.ck3workbench.app.NotificationsService.warn
import javax.swing.JFileChooser

/**
 * Takes care of the proper exit logic to run when the app is being closed
 * or shut down by other events
 */
class AppShutdownService {
    private val projectManager = ProjectManager()

    /**
     * Saves the current session and the associated active project
     */
    fun shutdown() {
        NotificationsService.notify("Running app shutdown")
        SessionHolder.activeSession.value.let { session ->
            if (session.activeProject?.location.isNullOrBlank()) {
                warn("Project has no location set")
                warn("The project will be lost")
                if (!askForSave(AppManager.focusedWindow!!.window)) {
                    notify("Project has not been saved before exit")
                }
            } else {
                projectManager.saveCurrentProject()
            }
            session.save()
        } ?: run {
            error("No session was found to be saved")
        }
    }

    private fun askForSave(window: ComposeWindow): Boolean {
        val fileChooser = JFileChooser()
        fileChooser.addChoosableFileFilter(ProjectFileFilter())

        when (fileChooser.showSaveDialog(window)) {
            JFileChooser.APPROVE_OPTION -> {
                NotificationsService.notify("Save project to file")
                val projectManager = ProjectManager()
                val sessionManager = SessionManager()
                val projectFile = fileChooser.selectedFile
                val projectToSave = Project()

                projectToSave.location = projectFile.absolutePath + ".wbp"
                projectManager.saveProject(projectToSave)
                sessionManager.activateProject(projectToSave)
                return true
            }
            JFileChooser.CANCEL_OPTION -> {
                notify("Cancel saving project before exit")
                return false
            }
        }
        error("Reached end of shutdown logic for some reason")
        return false
    }
}
