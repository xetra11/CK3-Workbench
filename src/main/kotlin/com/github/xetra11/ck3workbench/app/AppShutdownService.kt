package com.github.xetra11.ck3workbench.app

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
        SessionHolder.activeSession?.let { session ->
            session.save()
            projectManager.saveCurrentProject()
        } ?: run {
            NotificationsService.error("No session was found to be saved")
        }
    }
}
