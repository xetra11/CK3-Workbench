package com.github.xetra11.ck3workbench.app

import androidx.compose.desktop.AppManager
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

class SessionManagerTest : ShouldSpec({
    val sessionManager = SessionManager("test")

    afterTest {
        if (File("test.wbp").exists()) {
            File("test.wbp").delete()
        }
        StateManager.characters.clear()
    }

    should("create a project file on initializaton") {
        val expected = File("test.wbp")

        sessionManager.initialize()

        expected.exists() shouldBe true
        expected.isDirectory shouldBe false
        expected.extension shouldBe "wbp"
    }

    should("initialize project file with json projects array") {
        val projectFile = File("test.wbp")

        sessionManager.initialize()
        val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())

        projectFromFile shouldBe Project()
    }

    should("save character state on exit") {
        StateManager.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )

        val projectFile = File("test.wbp")

        sessionManager.initialize()
        sessionManager.onExit()

        val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())
        val (_, expectedCharacters) = Project(
            "default",
            characters =
                listOf(
                    CharacterTemplate.DEFAULT_CHARACTER,
                    CharacterTemplate.DEFAULT_CHARACTER
                )
        )

        projectFromFile.characters shouldBe expectedCharacters
    }

    should("load character state from file into state manager") {
        val projectFile = File("test.wbp")

        sessionManager.initialize()
        StateManager.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )

        sessionManager.onExit()
        StateManager.characters.clear()

        sessionManager.initialize()

        StateManager.characters shouldHaveSize 3
    }
})
