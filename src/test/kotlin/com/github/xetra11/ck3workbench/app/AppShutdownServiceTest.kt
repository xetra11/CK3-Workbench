package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.io.File
import java.nio.file.Paths

class AppShutdownServiceTest : ShouldSpec({
    val appShutdownService = AppShutdownService()

    beforeTest {
        deleteTestFiles()
    }

    afterTest {
        deleteTestFiles()
    }

    should("save current session on exit") {
        Project("Test", "project.wbp").save()
        val session = Session(SessionProject("project.wbp"))
        SessionHolder.activeSession = session

        val sessionFile = Paths.get("session.wbs").toFile()

        appShutdownService.shutdown()

        sessionFile.exists() shouldBe true
        sessionFile.isDirectory shouldBe false
        sessionFile.extension shouldBe "wbs"
    }

    should("save active project on exit if project exists") {
        Project("Test", "project.wbp").save()
        val session = Session(SessionProject("project.wbp"))
        SessionHolder.activeSession = session

        val projectFile = Paths.get("project.wbp").toFile()

        appShutdownService.shutdown()

        projectFile.exists() shouldBe true
        projectFile.isDirectory shouldBe false
        projectFile.extension shouldBe "wbp"
    }
})

private fun deleteTestFiles() {
    listOf("project.wbp", "session.wbs").forEach { file ->
        if (File(file).exists()) {
            File(file).delete()
        }
    }
}
