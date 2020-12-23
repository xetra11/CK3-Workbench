package com.github.xetra11.ck3workbench.app

import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.collections.shouldHaveSize
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
        StateManager.characters.clear()
    }

    should("create a session file on initialization") {
        val expected = File("session.wbp")

        sessionManager.initialize()

        expected.exists() shouldBe true
        expected.isDirectory shouldBe false
        expected.extension shouldBe "wbp"
    }

    should("initialize project file with json projects array") {
        val projectFile = File("session.wbp")

        sessionManager.initialize()
        val projectFromFile = Json.decodeFromString<Session>(projectFile.readText())

        projectFromFile shouldBe Session()
    }

    should("save character state on exit") {
        StateManager.characters.addAll(
            listOf(
                CharacterTemplate.DEFAULT_CHARACTER,
                CharacterTemplate.DEFAULT_CHARACTER
            )
        )

        val projectFile = File("session.wbp")

        sessionManager.initialize()
        sessionManager.onExit()

        val projectFromFile = Json.decodeFromString<Session>(projectFile.readText())
        val (_, expectedCharacters) = Session(
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
