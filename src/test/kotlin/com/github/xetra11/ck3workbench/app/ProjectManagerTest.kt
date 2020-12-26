package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

class ProjectManagerTest : ShouldSpec({
    val projectManager = ProjectManager()

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
        StateHolder.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )
        val projectFilePath = Paths.get("test.wbp").toAbsolutePath()
        SessionHolder.activeSession = Session(Project("Test Project", projectFilePath.toString()))

        projectManager.saveCurrentProject()

        val projectFromFile = Json.decodeFromString<Project>(projectFilePath.toFile().readText())
        val expectedProject = SessionHolder.activeSession!!.activeProject

        projectFromFile shouldBe expectedProject
    }

    should("have project data structure containing filepath to project file") {
        val projectPath = Paths.get("test.wbp").toAbsolutePath()
        val newProject = projectManager.saveNewProject("New Project", projectPath, "This is a project")

        newProject.location shouldBe Paths.get("test.wbp").toAbsolutePath().toString()
    }

    should("open an existing project and init character list and add it to active project") {
        SessionHolder.activeSession = Session(Project())
        val expectedCharacters = listOf(
            CharacterTemplate.DEFAULT_CHARACTER,
            CharacterTemplate.DEFAULT_CHARACTER
        )
        val testProject = Project(
            "My Project",
            Paths.get("test.wbp").toAbsolutePath().toString(),
            state = ProjectState(expectedCharacters)
        )

        projectManager.saveProject(testProject)
        val loadedProject = projectManager.loadProject(testProject)

        loadedProject.state.characters shouldBe expectedCharacters
        SessionHolder.activeSession shouldNotBe null
        SessionHolder.activeSession!!.activeProject shouldNotBe null
        SessionHolder.activeSession!!.activeProject shouldBe loadedProject
    }

    should("add loaded project to the recent projects list of the session") {
        SessionHolder.activeSession = Session(Project())
        val projectOne = Project("Project 1", Paths.get("project_1.wbp").toAbsolutePath().toString())
        val projectTwo = Project("Project 1", Paths.get("project_1.wbp").toAbsolutePath().toString())

        projectManager.saveProject(projectOne)
        projectManager.saveProject(projectTwo)

        projectManager.loadProject(projectOne)
        SessionHolder.activeSession!!.activeProject shouldBe projectOne

        projectManager.loadProject(projectTwo)
        SessionHolder.activeSession!!.activeProject shouldBe projectTwo

        SessionHolder.activeSession!!.recentProjects.size shouldBeExactly 2
        SessionHolder.activeSession!!.recentProjects shouldContainInOrder listOf(projectOne, projectTwo)
    }
})

private fun deleteTestFile() {
    listOf("test.wbp", "project_1.wbp", "project_2.wbp").forEach { file ->
        if (File(file).exists()) {
            File(file).delete()
        }
    }
}
