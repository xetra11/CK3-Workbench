package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CK3Character
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.Paths

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

fun Project.fromPath(path: Path): Project {
    val projectData = path.toFile().readText()
    return Json.decodeFromString<Project>(projectData)
}

fun Project.save() {
    val projectData = Json.encodeToString(this)
    Paths.get(this.location).toFile().writeText(projectData, Charsets.UTF_8)
}
