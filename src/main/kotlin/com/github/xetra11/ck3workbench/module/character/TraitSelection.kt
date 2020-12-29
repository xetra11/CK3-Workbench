package com.github.xetra11.ck3workbench.module.character

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.github.xetra11.ck3workbench.app.styles.WorkbenchTexts.BasicButtonText

/**
 * Holds the trait selection state and provides the trait button composable
 */
class TraitSelection {
    private val selectionState = mutableMapOf<Trait, Boolean>()

    enum class Trait(val code: String, val label: String) {
        BRAVE("brave", "Brave"),
        CRAVEN("crave", "Craven"),
        CALM("calm", "Calm"),
        WRATHFUL("wrathful", "Wrathful"),
        CHASTE("chaste", "Chaste"),
        LUSTFUL("lustful", "Lustful"),
        CONTENT("content", "Content"),
        AMBITIOUS("ambitious", "Ambitious"),
        DILIGENT("diligent", "Diligent"),
        LAZY("lazy", "Lazy"),
        FICKLE("fickle", "Fickle"),
        STUBBORN("stubborn", "Stubborn"),
        FORGIVING("forgiving", "Forgiving"),
        VENGEFUL("vengeful", "Vengeful"),
        GENEROUS("generous", "Generous"),
        GREEDY("greedy", "Greedy"),
        GREGARIOUS("gregarious", "Gregarious"),
        SHY("shy", "Shy"),
        HONEST("honest", "Honest"),
        DECEITFUL("deceitful", "Deceitful"),
        HUMBLE("humble", "Humble"),
        ARROGANT("arrogant", "Arrogant"),
        JUST("just", "Just"),
        ARBITRARY("arbitrary", "Arbitrary"),
        PATIENT("patient", "Patient"),
        IMPATIENT("impatient", "Impatient"),
        TEMPERATE("temperate", "Temperate"),
        GLUTTONOUS("gluttonous", "Gluttonous"),
        TRUSTING("trusting", "Trusting"),
        PARANOID("paranoid", "Paranoid"),
        ZEALOUS("zealous", "Zealous"),
        CYNICAL("cynical", "Cynical"),
        COMPASSIONATE("compassionate", "Compassionate"),
        CALLOUS("callous", "Callous"),
        SADISTIC("sadistic", "Sadistic"),
    }

    /**
     * Access to the current selection of traits
     */
    fun selection(): MutableMap<Trait, Boolean> {
        return this.selectionState
    }

    @Composable
    fun TraitButtons() {
        val chunks: List<List<Trait>> = enumValues<Trait>().toList().chunked(5)
        chunks.forEach {
            Row {
               it.forEach {
                   TraitButton(it)
               }
            }
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
            BasicButtonText(trait.label)
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
