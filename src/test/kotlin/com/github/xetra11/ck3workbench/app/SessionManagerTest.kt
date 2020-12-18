package com.github.xetra11.ck3workbench.app

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.engine.spec.tempfile
import io.kotest.matchers.paths.exist
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import java.io.File

class SessionManagerTest : ShouldSpec({
    val sessionManager = SessionManager()

    afterTest {
        if (File("project.wbp").exists()) {
            File("project.wbp").delete()
        }
    }

    should("have a file created") {
        val expected = File("project.wbp")

        sessionManager.initialize()

        expected.exists() shouldBe true
        expected.isDirectory shouldBe false
        expected.extension shouldBe "wbp"
    }

})
