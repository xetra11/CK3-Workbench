package com.github.xetra11.ck3workbench.module.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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

/**
 * Holds the trait selection state and provides the trait button composable
 */
class TraitSelection {
    private val traitIconPath = "icons/trait_icons"

    enum class Trait(val code: String, val label: String) {
        BRAVE("brave", "Brave"),
        CRAVEN("craven", "Craven"),
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

    @Composable
    fun TraitIcons(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks: List<List<Trait>> = enumValues<Trait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    private fun TraitIcon(trait: Trait, selectionState: SnapshotStateMap<Trait, Boolean>) {
        var isSelected by remember { mutableStateOf(false) }
        var selectionModifier by remember { mutableStateOf(Modifier.alpha(1F)) }

        Box(
            Modifier.clickable(
                onClick = {
                    isSelected = !isSelected
                    selectionState[trait] = isSelected
                    selectionModifier = if (isSelected) Modifier.alpha(0.2F) else Modifier.alpha(1F)
                }
            ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = selectionModifier,
                bitmap = traitImage(trait)
            )
            if (isSelected) {
                Text(
                    fontSize = TextUnit.Em(0.7),
                    text = trait.label
                )
            }
        }
    }

    private fun traitImage(trait: Trait): ImageBitmap {
        return imageFromResource(iconPath(trait.code))
    }

    private fun iconPath(traitCode: String): String {
        return "$traitIconPath/60px-Trait_$traitCode.png"
    }
}
