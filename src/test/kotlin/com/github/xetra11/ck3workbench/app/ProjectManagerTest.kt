package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import java.io.File
import java.nio.file.Paths

class ProjectManagerTest : ShouldSpec({
    val projectManager: ProjectManager = ProjectManager()

    afterTest {
        deleteTestFile()
    }

    should("save as project with name") {
        val workDir = Paths.get("").toAbsolutePath()
        projectManager.createProject("test", workDir, "my description")

        val expected = File("test.wbp")

        expected.exists() shouldBe true
    }

    xshould("save current project save") { }
    xshould("open an existing project") { }
    xshould("list recent projects that are saved in session") { }
})

private fun deleteTestFile() {
    if (File("test.wbp").exists()) {
        File("test.wbp").delete()
    }
}
