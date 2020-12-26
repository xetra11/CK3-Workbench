package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import java.nio.file.Paths

class SessionManagerTest : ShouldSpec({
    val sessionManager = SessionManager()

    beforeTest() {
        if (File("session.wbs").exists()) {
            File("session.wbs").delete()
        }
    }

    should("create new session file if not existing") {
        val sessionFile = File("session.wbs")

        sessionFile.exists() shouldBe false

        sessionManager.load()

        sessionFile.exists() shouldBe true
        sessionFile.isDirectory shouldBe false
        sessionFile.extension shouldBe "wbs"

        val sessionFromFile = Json.decodeFromString<Session>(sessionFile.readText())
        sessionFromFile shouldBe Session()
    }

    should("save current project on exit") {
        val sessionFile = File("session.wbs")
        val currentProject = Project("myProject", Paths.get("").toString(), "The description")

        val session = sessionManager.load()
        SessionHolder.activeSession = session
        SessionHolder.activeSession!!.activeProject shouldBe null

        SessionHolder.activeSession!!.activeProject = currentProject
        sessionManager.exit()

        val sessionFromFile = Json.decodeFromString<Session>(sessionFile.readText())

        sessionFile.exists() shouldBe true
        sessionFile.isDirectory shouldBe false
        sessionFile.extension shouldBe "wbs"
        sessionFromFile.activeProject shouldBe currentProject
    }
})
