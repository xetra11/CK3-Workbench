package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.app.project.Project
import com.github.xetra11.ck3workbench.app.project.ProjectManager
import com.github.xetra11.ck3workbench.app.project.ProjectState
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainInOrder
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
        projectManager.createProject("Test Project", workDir, "my description")

        val expected = File("test.wbp")

        expected.exists() shouldBe true
    }

    should("save current project") {
        val projectFilePath = Paths.get("test.wbp").toAbsolutePath()
        projectManager.createProject("Test Project", projectFilePath, "my description")

        StateHolder.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )
        SessionHolder.activeSession.value = Session(SessionProject(projectFilePath.toString()))

        projectManager.saveCurrentProject()

        val projectFromFile = Json.decodeFromString<Project>(projectFilePath.toFile().readText())
        val expectedProject = SessionHolder.activeSession.value.activeProject?.loadProject()

        projectFromFile shouldBe expectedProject
    }

    should("have project data structure containing filepath to project file") {
        val projectPath = Paths.get("test.wbp").toAbsolutePath()
        val newProject = projectManager.createProject("New Project", projectPath, "This is a project")

        newProject.location shouldBe Paths.get("test.wbp").toAbsolutePath().toString()
    }

    should("load project state into stateholder") {
        StateHolder.characters.clear()
        val project = Project(
            "Test",
            "test.wbp",
            state = ProjectState(
                listOf(
                    CharacterTemplate.DEFAULT_CHARACTER,
                    CharacterTemplate.DEFAULT_CHARACTER
                )
            )
        )

        projectManager.load(project)

        StateHolder.characters shouldContainInOrder project.state.characters
    }
})

private fun deleteTestFile() {
    listOf("test.wbp", "project_1.wbp", "project_2.wbp").forEach { file ->
        if (File(file).exists()) {
            File(file).delete()
        }
    }
}
