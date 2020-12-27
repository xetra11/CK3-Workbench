package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

class ProjectManagerTest : ShouldSpec({
    val projectManager = ProjectManager()

    beforeTest() {
        deleteTestFile()
    }

    afterTest {
        deleteTestFile()
    }

    should("save as project with name") {
        val workDir = Paths.get("test.wbp").toAbsolutePath()
        projectManager.saveNewProject("Test Project", workDir, "my description")

        val expected = File("test.wbp")

        expected.exists() shouldBe true
    }

    should("save current project") {
        val projectFilePath = Paths.get("test.wbp").toAbsolutePath()
        projectManager.saveNewProject("Test Project", projectFilePath, "my description")

        StateHolder.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )
        SessionHolder.activeSession = Session(SessionProject(projectFilePath.toString()))

        projectManager.saveCurrentProject()

        val projectFromFile = Json.decodeFromString<Project>(projectFilePath.toFile().readText())
        val expectedProject = SessionHolder.activeSession!!.activeProject.toProject()

        projectFromFile shouldBe expectedProject
    }

    should("have project data structure containing filepath to project file") {
        val projectPath = Paths.get("test.wbp").toAbsolutePath()
        val newProject = projectManager.saveNewProject("New Project", projectPath, "This is a project")

        newProject.location shouldBe Paths.get("test.wbp").toAbsolutePath().toString()
    }
})

private fun deleteTestFile() {
    listOf("test.wbp", "project_1.wbp", "project_2.wbp").forEach { file ->
        if (File(file).exists()) {
            File(file).delete()
        }
    }
}
