package com.github.xetra11.ck3workbench.module.character

import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.NotificationsService.notify

/**
 * Holds the trait selection state and provides the trait button composable
 */
class TraitSelection {
    private val selectionState = mutableMapOf<Trait, Boolean>()

    enum class Trait(val code: String, val label: String) {
        AMBITIOUS("ambitious", "Ambitious"),
        HUNTER_1("hunter_1", "Hunter 1"),
        WRATHFUL("wrathful", "Wrathful"),
        CALLOUS("callous", "Callous"),
        VIKING("viking", "Viking"),
        EDUCATION_MARTIAL_3("education_martial_3", "Education Martial 3")
    }

    /**
     * Access to the current selection of traits
     */
    fun selection(): MutableMap<Trait, Boolean> {
        return this.selectionState
    }

    @Composable
    fun TraitButtons() {
        enumValues<Trait>().forEach {
            TraitButton(it)
        }
    }

    @Composable
    private fun TraitButton(trait: Trait) {
        var color by remember { mutableStateOf(Color.LightGray) }
        Button(
            modifier = Modifier.size(100.dp, 50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color
            ),
            onClick = {
                color = toggleSelection(color, trait)
            }
        ) {
            Text(trait.label)
        }
    }

    private fun toggleSelection(colorparam: Color, trait: Trait): Color {
        val color = toggleColor(colorparam)
        selectionState[trait] = color == Color.Green
        notify(selectionState.size.toString())
        return color
    }

    private fun toggleColor(color: Color): Color {
        return if (color == Color.LightGray) Color.Green else Color.LightGray
    }
}
