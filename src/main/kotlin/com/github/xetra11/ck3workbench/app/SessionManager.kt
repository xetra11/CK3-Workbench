package com.github.xetra11.ck3workbench.app

import java.io.File

/**
 * Deals with loading project files and assets to persist the state of the application
 */
class SessionManager {
    fun initialize() {
        File("project.wbp").createNewFile()
    }
}
