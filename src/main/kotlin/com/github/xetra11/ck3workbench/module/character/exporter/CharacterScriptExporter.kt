package com.github.xetra11.ck3workbench.module.character.exporter

import com.github.xetra11.ck3workbench.app.StateManager
import com.github.xetra11.ck3workbench.app.exporter.ScriptExporter
import com.github.xetra11.ck3workbench.module.character.CK3Character.Skill
import java.io.File

class CharacterScriptExporter(
    private val exportFileName: String = "characters"

) : ScriptExporter {

    override fun export() {
        val exportFile = File("$exportFileName.txt")
        if (exportFile.exists()) {
            exportFile.delete()
        }
        exportFile.createNewFile()
        exportFile.writeText(characterData())
    }

    private fun characterData(): String {
        return StateManager.characters.mapIndexed { index, character ->
            """
$index = {
    ${character.name.toScriptAttribute("name", true)}
    ${character.dna.toScriptAttribute("dna")}
    ${character.dynasty.toScriptAttribute("dynasty")}
    ${character.religion.toScriptAttribute("religion", true)}
    ${character.culture.toScriptAttribute("culture")}
    
    ${character.skills.toSkillAttributes()}
    ${character.traits.toTraitAttributes()}
    ${character.birth.toDateAttribute("birth")}
    ${character.death.toDateAttribute("death")}
}
            """.trimIndent()
        }.joinToString(separator = "") { it + "\n\n" }
    }

    private fun String.toDateAttribute(id: String): String {
        val date =
"""
    $this = {
        $id = yes
    }
""".trim()
        return date
    }

    private fun String.toScriptAttribute(id: String, withQuotation: Boolean = false): String {
        return if (withQuotation) """$id = "$this"""" else "$id = $this"
    }

    private fun Map<Skill, Short>.toSkillAttributes(): String {
        return this.map { (skill, value) ->
            """${skill.name.toLowerCase()} = $value"""
        }.joinToString(separator = "") { it + NEWLINE }
    }

    private fun List<String>.toTraitAttributes(): String {
        return this.map { trait ->
            """trait = $trait"""
        }.joinToString(separator = "") { it + NEWLINE }
    }

    companion object {
        private const val NEWLINE = "\n    "
    }
}
