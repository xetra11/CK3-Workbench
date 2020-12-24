package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class SessionManagerTest : ShouldSpec({
    val sessionManager = SessionManager()

    afterTest {
        if (File("session.wbp").exists()) {
            File("session.wbp").delete()
        }
    }

    should("initialize new session file") {
        val sessionFile = File("session.wbp")

        sessionManager.initialize()
        val sessionFromFile = Json.decodeFromString<Session>(sessionFile.readText())

        sessionFile.exists() shouldBe true
        sessionFile.isDirectory shouldBe false
        sessionFile.extension shouldBe "wbp"
        sessionFromFile shouldBe Session()
    }

    should("save current project on exit") {
        val sessionFile = File("session.wbp")
        val currentProject = Project("myProject", "The description")

        sessionManager.initialize()
        sessionManager.exit(currentProject)

        val sessionFromFile = Json.decodeFromString<Session>(sessionFile.readText())

        sessionFile.exists() shouldBe true
        sessionFile.isDirectory shouldBe false
        sessionFile.extension shouldBe "wbp"
        sessionFromFile.activeProject shouldBe currentProject
    }

    should("load saved session file into sessionholder") {
        val sessionFile = File("session.wbp")
        val currentProject = Project("myProject", "The description")

        sessionManager.initialize()
        sessionManager.exit(currentProject)
        sessionManager.initialize()

        val currentSession: Session = SessionHolder.activeSession ?: Session()

        currentSession.activeProject shouldBe currentProject
    }

    should("save recent projects") {}
})
