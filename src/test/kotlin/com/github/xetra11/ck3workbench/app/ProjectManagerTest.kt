package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
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
        val workDir = Paths.get("").toAbsolutePath()
        projectManager.saveNewProject("test", workDir, "my description")

        val expected = File("test.wbp")

        expected.exists() shouldBe true
    }

    xshould("save current project") {
        StateManager.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )

        //every { sessionManager }

        projectManager.saveCurrentProject()
    }

    xshould("open an existing project") { }
    xshould("list recent projects that are saved in session") { }
})

private fun deleteTestFile() {
    if (File("test.wbp").exists()) {
        File("test.wbp").delete()
    }
}