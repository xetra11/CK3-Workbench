package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CK3Character
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.channels.FileChannel

/**
 * Deals with loading project files and assets to persist the state of the application
 */
class SessionManager {
    fun initialize() {
        val projectFile = File("project.wbp")
        if (!projectFile.exists()) projectFile.createNewFile()

        val projectData = Json.encodeToString(Project())

        projectFile.writeText(projectData)
    }
}

@Serializable
data class Project(
    var name: String = "default"
)

