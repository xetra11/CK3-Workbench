package com.github.xetra11.ck3workbench.module.character

import kotlinx.serialization.Serializable

/**
 * Representation of a CK3 script history/character
 *
 * @author Patrick C. Höfer
 */

@Serializable
data class CK3Character(
    var name: String,
    var dna: String,
    var dynasty: String,
    var religion: String,
    var culture: String,
    var skills: Map<Skill, Short>,
    var traits: List<String>,
    var birth: String,
    var death: String
) {
    enum class Skill {
        MARTIAL,
        STEWARDSHIP,
        DIPLOMACY,
        INTRIGUE,
        LEARNING
    }
    companion object {
        fun from(map: Map<String, String>): CK3Character {
            val name by map
            val dna by map
            val dynasty by map
            val religion by map
            val culture by map

            return CK3Character(
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
