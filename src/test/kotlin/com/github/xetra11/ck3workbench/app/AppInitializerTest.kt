package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.app.project.Project
import com.github.xetra11.ck3workbench.app.project.ProjectManager
import com.github.xetra11.ck3workbench.app.project.ProjectState
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class AppInitializerTest : ShouldSpec({
    val sessionManager = SessionManager()
    val projectManager = ProjectManager()
    val appInitializer = AppInitializer(sessionManager, projectManager)

    beforeTest {
        deleteTestFiles()
    }

    afterTest {
        deleteTestFiles()
    }

    should("load the last session") {
        val expectedProject = Project("Test Project", location = "test.wbp")
        val expectedSession = Session(activeProject = SessionProject(expectedProject.location))

        val sessionData = Json.encodeToString(expectedSession)
        val projectData = Json.encodeToString(expectedProject)

        val projectFile = File(expectedProject.location)
        val sessionFile = File("session.wbs")

        projectFile.writeText(projectData, Charsets.UTF_8)
        sessionFile.writeText(sessionData, Charsets.UTF_8)

        appInitializer.initialize()

        SessionHolder.activeSession.value shouldBe expectedSession
    }

    should("load the last session and load given project file") {
        val characters = listOf(
            CharacterTemplate.DEFAULT_CHARACTER,
            CharacterTemplate.DEFAULT_CHARACTER
        )
        val expectedProject = Project("Test Project", location = "test.wbp", state = ProjectState(characters))
        val expectedSession = Session(activeProject = SessionProject(expectedProject.location))

        val sessionData = Json.encodeToString(expectedSession)
        val projectData = Json.encodeToString(expectedProject)

        val projectFile = File(expectedProject.location)
        val sessionFile = File("session.wbs")

        projectFile.writeText(projectData, Charsets.UTF_8)
        sessionFile.writeText(sessionData, Charsets.UTF_8)

        appInitializer.initialize()

        SessionHolder.activeSession.value shouldBe expectedSession
        StateHolder.characters shouldContainExactly characters
    }

    should("initialize settings.cfg file if not created already") {
        val file = File("settings.cfg")

        appInitializer.initialize()

        file.exists() shouldBe true
    }

    should("load settings and set to SettingsHolder") {
        val expected = AppSettings(true)
        expected.save()

        appInitializer.initialize()

        SettingsHolder.toAppSettings() shouldBe expected
    }
})

private fun deleteTestFiles() {
    listOf("test.wbp", "session.wbs", "default.wbp", "settings.cfg").forEach { file ->
        if (File(file).exists()) {
            File(file).delete()
        }
    }
}
