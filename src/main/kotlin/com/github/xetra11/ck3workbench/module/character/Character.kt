package com.github.xetra11.ck3workbench.module.character

/**
 * Representation of a CK3 script history/character
 *
 * @author Patrick C. Höfer
 */

data class Character(
    val name: String,
    val dna: String,
    val dynasty: String,
    val religion: String,
    val culture: String,
    val skills: Map<Skill, Short>,
    val traits: List<String>,
    val birth: String,
    val death: String
) {
    enum class Skill {
        MARTIAL,
        STEWARDSHIP,
        DIPLOMACY,
        INTRIGUE,
        LEARNING
    }
    companion object {
        fun from(map: Map<String, String>): Character {
            val name by map
            val dna by map
            val dynasty by map
            val religion by map
            val culture by map

            return Character(
                name,
                dna,
                dynasty,
                religion,
                culture,
                mapOf(),
                listOf(),
                "",
                ""
            )
        }
    }
}
