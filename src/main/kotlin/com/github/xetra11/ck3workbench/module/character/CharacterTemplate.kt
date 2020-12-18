package com.github.xetra11.ck3workbench.module.character

/**
 * A group of *members*.
 *
 * This class has no useful logic; it's just a documentation example.
 *
 * @param T the type of a member in this group.
 * @property name the name of this group.
 * @constructor Creates an empty group.
 * @author Patrick C. Höfer
 */
object CharacterTemplate {
    val DEFAULT_CHARACTER = CK3Character(
        "Thorak",
        "thorak_dna",
        "my_dynastie",
        "asatru",
        "cheruscii",
        skills = mapOf(
            CK3Character.Skill.DIPLOMACY to 5,
            CK3Character.Skill.MARTIAL to 5,
            CK3Character.Skill.STEWARDSHIP to 5,
            CK3Character.Skill.INTRIGUE to 5,
            CK3Character.Skill.LEARNING to 5,
        ),
        traits = listOf(
            "ambitious",
            "hunter_1",
            "wrathful",
            "callous",
            "viking",
            "education_martial_3"
        ),
        "763.1.1",
        "800.1.1"
    )
}
