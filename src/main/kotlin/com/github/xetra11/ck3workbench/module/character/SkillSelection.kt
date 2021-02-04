package com.github.xetra11.ck3workbench.module.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Holds skill button composables
 */
class SkillSelection {

    enum class Skill(val code: String, val label: String) {
        MARTIAL("martial", "Martial"),
        STEWARDSHIP("stewardship", "StewardShip"),
        DIPLOMACY("diplomacy", "Diplomacy"),
        INTRIGUE("intrigue", "Intrigue"),
        LEARNING("learning", "Learning"),
        // PROWESS("prowess", "Prowess")
    }

    @Composable
    fun Skills(
        selectionState: SnapshotStateMap<Skill, Int>
    ) {
        val chunks = enumValues<Skill>().toList().chunked(6)
        chunks.forEach {
            Row {
                it.forEach {
                    SkillIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun SkillIcon(
        skill: Skill,
        selectionState: SnapshotStateMap<Skill, Int>
    ) {
        var counter by remember { mutableStateOf(5) }
        selectionState[skill] = counter

        Column(
            modifier = Modifier.size(75.dp, 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(contentDescription = "", modifier = Modifier.size(60.dp), bitmap = skillImage(skill))
            }
            Row {
                Text(modifier = Modifier.clickable { counter = if (counter <= 1) 0 else counter.minus(1) }, text = "<")
                Text(counter.toString())
                Text(
                    modifier = Modifier.clickable { counter = if (counter >= 100) 100 else counter.plus(1) },
                    text = ">"
                )
            }
        }
    }

    @Composable
    fun SkillLabel(skill: Skill) {
        Text(
            fontSize = TextUnit.Em(0.7),
            text = skill.label
        )
    }

    private fun skillImage(skill: Skill): ImageBitmap {
        return imageFromResource(iconPath(skill.code))
    }

    private fun iconPath(skillCode: String): String {
        return "$skillIconPath/$skillCode.png"
    }

    companion object {
        const val skillIconPath = "icons/skill_icons"
    }
}
