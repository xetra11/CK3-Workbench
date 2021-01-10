package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.app.project.Project
import com.github.xetra11.ck3workbench.app.project.save
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
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
        SessionHolder.activeSession.value = session

        val sessionFile = Paths.get("session.wbs").toFile()

        appShutdownService.shutdown()

        sessionFile.exists() shouldBe true
        sessionFile.isDirectory shouldBe false
        sessionFile.extension shouldBe "wbs"
    }

    should("save active project on exit if project exists") {
        Project("Test", "project.wbp").save()
        val session = Session(SessionProject("project.wbp"))
        SessionHolder.activeSession.value = session

        val projectFile = Paths.get("project.wbp").toFile()

        appShutdownService.shutdown()

        projectFile.exists() shouldBe true
        projectFile.isDirectory shouldBe false
        projectFile.extension shouldBe "wbp"
    }

    should("save settings on exit in session") {
        Project("Test", "project.wbp").save()
        val session = Session(SessionProject("project.wbp"))
        SessionHolder.activeSession.value = session
        SettingsHolder.autosave = true
        val settingsFile = File("settings.cfg")
        val expectedSettings = AppSettings(autosave = true)
        settingsFile.createNewFile()

        appShutdownService.shutdown()

        val actualSettings = Json.decodeFromString<AppSettings>(settingsFile.readText(Charsets.UTF_8))

        settingsFile.exists() shouldBe true
        actualSettings shouldBe expectedSettings
    }
})

private fun deleteTestFiles() {
    listOf("project.wbp", "session.wbs", "settings.cfg").forEach { file ->
        if (File(file).exists()) {
            File(file).delete()
        }
    }
}
