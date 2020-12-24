package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.mockk
import java.io.File
import java.nio.file.Paths

class ProjectManagerTest : ShouldSpec({
    val sessionManager: SessionManager = mockk()
    val projectManager: ProjectManager = ProjectManager(sessionManager)

    afterTest {
        deleteTestFile()
        clearMocks(sessionManager)
    }

    should("save as project with name") {
        val workDir = Paths.get("test.wbp").toAbsolutePath()
        projectManager.saveNewProject("Test Project", workDir, "my description")

        val expected = File("test.wbp")

        expected.exists() shouldBe true
    }

    xshould("save current project") {
        StateHolder.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )

        //every { sessionManager }

        projectManager.saveCurrentProject()
    }

    should("have project data structure containing filepath to project file") {
        val projectPath = Paths.get("test.wbp").toAbsolutePath()
        val newProject = projectManager.saveNewProject("New Project", projectPath, "This is a project")

        newProject.location shouldBe Paths.get( "test.wbp" ).toAbsolutePath().toString()
    }

    xshould("open an existing project and init character list") { }
    xshould("list recent projects that are saved in session") { }
})

private fun deleteTestFile() {
    if (File("test.wbp").exists()) {
        File("test.wbp").delete()
    }
}
