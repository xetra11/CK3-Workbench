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

    interface Trait {
        val code: String
        val label: String
    }

    enum class EducationalTrait(override val code: String, override val label: String) : Trait {
        DIPLOMACY("diplomacy", "Naive Appeaser"),
        INTRIGUE("intrigue", "Amateurish Plotter"),
        MARTIAL("martial", "Misguided Warrior"),
        STEWARDSHIP("stewardship", "Indulgent Wrastel"),
        PROWESS("martial_prowess", "Bumbling Squire"),

        DIPLOMACY_1("diplomacy_1", "Naive Appeaser"),
        DIPLOMACY_2("diplomacy_2", "Adequate Appeaser"),
        DIPLOMACY_3("diplomacy_3", "Charismatic Appeaser"),
        DIPLOMACY_4("diplomacy_4", "Grey Eminence"),

        INTRIGUE_1("intrigue_1", "Amateurish Plotter"),
        INTRIGUE_2("intrigue_2", "Flamboyant Trickster"),
        INTRIGUE_3("intrigue_3", "Intricate Webweaver"),
        INTRIGUE_4("intrigue_4", "Elusive Shadow"),

        MARTIAL_1("martial_1", "Misguided Warrior"),
        MARTIAL_2("martial_2", "Tough Soldier"),
        MARTIAL_3("martial_3", "Skilled Tactician"),
        MARTIAL_4("martial_4", "Brilliant Strategist"),

        STEWARDSHIP_1("stewardship_1", "Indulgent Wrastel"),
        STEWARDSHIP_2("stewardship_2", "Thrifty Clerk"),
        STEWARDSHIP_3("stewardship_3", "Fortune Builder"),
        STEWARDSHIP_4("stewardship_4", "Midas Touched"),

        PROWESS_1("martial_prowess_1", "Bumbling Squire"),
        PROWESS_2("martial_prowess_2", "Confident Knight"),
        PROWESS_3("martial_prowess_3", "Formidable Banneret"),
        PROWESS_4("martial_prowess_4", "Famous Champion"),
    }

    enum class PersonalityTrait(override val code: String, override val label: String) : Trait {
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
    fun PersonalityTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<PersonalityTrait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun EducationalTrait(
        selectionState: SnapshotStateMap<EducationalTrait, Int>
    ) {
        Row {
            TraitIcon(EducationalTrait.DIPLOMACY, selectionState)
            TraitIcon(EducationalTrait.INTRIGUE, selectionState)
            TraitIcon(EducationalTrait.STEWARDSHIP, selectionState)
            TraitIcon(EducationalTrait.MARTIAL, selectionState)
            TraitIcon(EducationalTrait.PROWESS, selectionState)
        }
    }

    @Composable
    private fun TraitIcon(
        educationalTrait: EducationalTrait,
        selectionState: SnapshotStateMap<EducationalTrait, Int>
    ) {
        var rank by remember { mutableStateOf(1) }
        var selectionModifier by remember { mutableStateOf(Modifier.alpha(1F)) }

        Box(
            Modifier.clickable(
                onClick = {
                    rank = rankUp(rank)
                    selectionState[educationalTrait] = rank
                    selectionModifier = if (rank == 0) Modifier.alpha(0.2F) else Modifier.alpha(1F)
                }
            ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = selectionModifier,
                bitmap = traitImage(educationalTrait, rank)
            )
        }
    }

    private fun rankUp(rank: Int): Int {
        return if (rank < 4) rank.plus(1) else 0
    }

    @Composable
    private fun TraitIcon(
        personalityTrait: PersonalityTrait,
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        var isSelected by remember { mutableStateOf(false) }
        var selectionModifier by remember { mutableStateOf(Modifier.alpha(1F)) }

        Box(
            Modifier.clickable(
                onClick = {
                    isSelected = !isSelected
                    selectionState[personalityTrait] = isSelected
                    selectionModifier = if (isSelected) Modifier.alpha(0.2F) else Modifier.alpha(1F)
                }
            ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = selectionModifier,
                bitmap = traitImage(personalityTrait)
            )
            if (isSelected) {
                Text(
                    fontSize = TextUnit.Em(0.7),
                    text = personalityTrait.label
                )
            }
        }
    }

    private fun traitImage(personalityTrait: PersonalityTrait): ImageBitmap {
        return imageFromResource(personalityIconPath(personalityTrait.code))
    }

    private fun traitImage(educationalTrait: EducationalTrait, rank: Int): ImageBitmap {
        return imageFromResource(educationalIconPath(educationalTrait.code, rank))
    }

    private fun personalityIconPath(traitCode: String): String {
        return "$traitIconPath/60px-Trait_$traitCode.png"
    }

    private fun educationalIconPath(traitCode: String, rank: Int): String {
        return "$traitIconPath/Trait_education_${traitCode}_$rank.png"
    }
}
