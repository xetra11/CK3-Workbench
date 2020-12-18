package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.paths.exist
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.math.exp

class SessionManagerTest : ShouldSpec({
    val sessionManager = SessionManager()

    afterTest {
        if (File("project.wbp").exists()) {
            File("project.wbp").delete()
        }
    }

    should("create a project file on initializaton") {
        val expected = File("project.wbp")

        sessionManager.initialize()

        expected.exists() shouldBe true
        expected.isDirectory shouldBe false
        expected.extension shouldBe "wbp"
    }

    should("initialize project file with json projects array") {
        val projectFile = File("project.wbp")

        sessionManager.initialize()
        val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())

        projectFromFile shouldBe Project()
    }

})
