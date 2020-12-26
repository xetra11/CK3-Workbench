package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.math.exp

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

})
