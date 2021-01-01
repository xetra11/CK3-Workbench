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
import androidx.compose.ui.draw.alpha
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
        PROWESS("prowess", "Prowess")
    }

    @Composable
    fun Skills(
        selectionState: SnapshotStateMap<Skill, Boolean>
    ) {
        val chunks = enumValues<Skill>().toList().chunked(5)
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
        selectionState: SnapshotStateMap<Skill, Boolean>
    ) {
        var isSelected by remember { mutableStateOf(false) }
        var selectionModifier by remember { mutableStateOf(Modifier.alpha(0.2F)) }

        Column(
            modifier = Modifier.size(60.dp, 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.clickable(
                    onClick = {
                        isSelected = !isSelected
                        selectionState[skill] = isSelected
                        selectionModifier = if (!isSelected) Modifier.alpha(0.2F) else Modifier.alpha(1F)
                    }
                ),
                contentAlignment = Alignment.Center,
            ) {
                SkillIconImage(selectionModifier, skill)
            }
            if (isSelected) SkillLabel(skill)
        }
    }

    @Composable
    fun SkillIconImage(
        selectionModifier: Modifier,
        skill: Skill
    ) {
        Image(
            modifier = selectionModifier,
            bitmap = skillImage(skill)
        )
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
        val skillIconPath = "icons/skill_icons"
    }
}
