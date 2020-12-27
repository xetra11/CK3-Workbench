package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class SessionManagerTest : ShouldSpec({
    val sessionManager = SessionManager()

    beforeTest() {
        deleteTestFiles()
    }

    afterTest() {
        deleteTestFiles()
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

    should("save current session on exit") {
        val sessionFile = File("session.wbs")

        val session = sessionManager.load()
        SessionHolder.activeSession = session
        sessionManager.exit()

        val sessionFromFile = Json.decodeFromString<Session>(sessionFile.readText())

        sessionFile.exists() shouldBe true
        sessionFile.isDirectory shouldBe false
        sessionFile.extension shouldBe "wbs"
    }
})

private fun deleteTestFiles() {
    if (File("session.wbs").exists()) {
        File("session.wbs").delete()
    }
}
