package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class AppInitializerTest : ShouldSpec({
    val sessionManager = SessionManager()
    val appInitializer = AppInitializer(sessionManager)

    beforeTest {
        listOf("test.wbp", "session.wbs").forEach { file ->
            if (File(file).exists()) {
                File(file).delete()
            }
        }
    }

    should("load the last session") {
        val expectedSession = Session()
        val sessionData = Json.encodeToString(expectedSession)
        val sessionFile = File("session.wbs")
        sessionFile.writeText(sessionData, Charsets.UTF_8)

        appInitializer.initialize()

        SessionHolder.activeSession shouldBe expectedSession
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

        SessionHolder.activeSession shouldBe expectedSession
        StateHolder.characters shouldContainExactly characters
    }
})
