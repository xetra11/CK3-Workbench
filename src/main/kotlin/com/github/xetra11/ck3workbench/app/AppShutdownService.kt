package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import com.github.xetra11.ck3workbench.app.NotificationsService.warn
import com.github.xetra11.ck3workbench.app.dialog.DialogManager
import com.github.xetra11.ck3workbench.app.project.ProjectManager

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
        notify("Running app shutdown")
        prepareSettings()
        prepareSession()
    }

    private fun prepareSettings() {
        notify("Prepare settings for exit")
        val appSettings = SettingsHolder.toAppSettings()
        appSettings.save()
    }

    private fun prepareSession() {
        notify("Prepare session for exit")
        SessionHolder.activeSession.value.let { session ->
            if (session.activeProject?.location.isNullOrBlank()) {
                warn("Project has no location set")
                warn("The project will be lost")
                DialogManager.openDialog(DialogManager.Dialog.SAVE_BEFORE_EXIT)
            } else {
                projectManager.saveCurrentProject()
            }
            session.save()
        }
    }
}
