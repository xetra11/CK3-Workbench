package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CK3Character
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Manages project files to load/save different projects
 */
class ProjectManager(sessionManager: SessionManager) {

    fun saveNewProject(name: String, path: Path, description: String) {
        val newProject = Project(name, description)

        val projectData = Json.encodeToString(newProject)
        val projectFile = Paths.get(path.toString(), "$name.wbp").toFile()

        projectFile.writeText(projectData)
    }

    fun projects(): List<Project> {
        return listOf()
    }

    fun saveCurrentProject() {
        TODO("Not yet implemented")
    }
}

@Serializable
data class Project(
    var name: String = "default",
    var description: String = "",
    var state: ProjectState = ProjectState()
)

@Serializable
data class ProjectState(
    var characters: List<CK3Character> = listOf()
)
