package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.StateHolder
import com.github.xetra11.ck3workbench.app.ViewManager
import com.github.xetra11.ck3workbench.app.ViewManager.View.CHARACTER_VIEW
import com.github.xetra11.ck3workbench.app.ui.CustomComponents.Spoiler
import com.github.xetra11.ck3workbench.module.character.CK3Character
import com.github.xetra11.ck3workbench.module.character.TraitSelection
import com.github.xetra11.ck3workbench.module.character.TraitSelection.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun CharacterFactoryView() {
    val traitSelection = TraitSelection()

    val personalityTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val congenitalTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val physicalTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val lifestyleTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val commanderTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val criminalTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val copingTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val childhoodTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val healthTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val diseaseTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val dynastyTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }
    val descendantTraitSelectionState = remember { mutableStateMapOf<Trait, Boolean>() }

    val educationalTraitSelectionState = remember { mutableStateMapOf<LeveledTrait, Int>() }
    val leveledLifestyleTraitSelectionState = remember { mutableStateMapOf<LeveledTrait, Int>() }
    val leveledCongenitalTraitSelectionState = remember { mutableStateMapOf<LeveledTrait, Int>() }

    Column(
        modifier = Modifier.padding(top = 15.dp, bottom = 7.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val name = remember { mutableStateOf("") }
        val dna = remember { mutableStateOf("") }
        val religion = remember { mutableStateOf("") }
        val dynasty = remember { mutableStateOf("") }
        val culture = remember { mutableStateOf("") }
        val birth = remember { mutableStateOf("") }
        val death = remember { mutableStateOf("") }

        Row(
            Modifier.fillMaxHeight(0.2F).fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                fontSize = TextUnit.Em(1.5),
                fontWeight = FontWeight.Bold,
                text = "Character Factory (v1)"
            )
        }

        Row(
            modifier = Modifier.fillMaxHeight(0.8F).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.fillMaxWidth(0.3F)) {
                InputFields(name, dna, dynasty, religion, culture, birth, death)
            }
            val scrollState = rememberScrollState(0f)
            ScrollableColumn(
                Modifier.fillMaxWidth(0.7F),
                scrollState = scrollState,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(20.dp))

                Text(
                    fontSize = TextUnit.Em(1.2),
                    fontWeight = FontWeight.Bold,
                    text = "Traits"
                )

                Spacer(Modifier.height(20.dp))

                TraitSection("Personality Traits") {
                    traitSelection.Traits<PersonalityTrait>(personalityTraitSelectionState)
                }
                TraitSection("Commander Traits") {
                    traitSelection.Traits<CommanderTrait>(commanderTraitSelectionState)
                }
                TraitSection("Criminal Traits") {
                    traitSelection.Traits<CriminalTrait>(criminalTraitSelectionState)
                }
                TraitSection("Coping Traits") {
                    traitSelection.Traits<CopingTrait>(copingTraitSelectionState)
                }
                TraitSection("Health Traits") {
                    traitSelection.Traits<HealthTrait>(healthTraitSelectionState)
                }
                TraitSection("Dynasty Traits") {
                    traitSelection.Traits<DynastyTrait>(dynastyTraitSelectionState)
                }
                TraitSection("Descendant Traits") {
                    traitSelection.Traits<DescendantTrait>(descendantTraitSelectionState)
                }
                TraitSection("Disease Traits") {
                    traitSelection.Traits<DiseaseTrait>(diseaseTraitSelectionState)
                }
                TraitSection("Childhood Traits") {
                    traitSelection.Traits<ChildhoodTrait>(childhoodTraitSelectionState)
                }
                TraitSection("Educational Traits") {
                    traitSelection.EducationalTraits(educationalTraitSelectionState)
                }
                TraitSection("Physical Traits") {
                    traitSelection.Traits<PhysicalTrait>(physicalTraitSelectionState)
                }
                TraitSection("Lifestyle Traits") {
                    traitSelection.Traits<LifestyleTrait>(lifestyleTraitSelectionState)
                }
                TraitSection("Leveled Lifestyle Traits") {
                    traitSelection.LeveledLifestyleTraits(leveledLifestyleTraitSelectionState)
                }
                TraitSection("Congenital Traits") {
                    traitSelection.Traits<CongenitalTrait>(congenitalTraitSelectionState)
                }
                TraitSection("Leveled Congenital Traits") {
                    traitSelection.LeveledCongenitalTraits(leveledCongenitalTraitSelectionState)
                }

                Spacer(Modifier.height(20.dp))
            }
            VerticalScrollbar(
                modifier = Modifier.fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState)
            )
        }

        Spacer(Modifier.height(30.dp))

        // CharacterPreview(personalityTraitSelectionState)

        Row(
            Modifier.fillMaxWidth().fillMaxHeight(0.5F).background(Color(187, 187, 190)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CreateButton(
                name,
                dna,
                religion,
                dynasty,
                culture,
                birth,
                death,
                personalityTraitSelectionState,
                educationalTraitSelectionState
            )
            Button(enabled = false, onClick = { }) {
                Text("Randomize")
            }
            Button(enabled = false, onClick = { }) {
                Text("Batch Create")
            }
        }
    }
}

@Composable
private fun TraitSection(
    label: String = "",
    content: @Composable() (ColumnScope.() -> Unit),
) {
    Spoiler(
        Modifier.padding(vertical = 5.dp),
        label = {
            Box(
                Modifier.background(Color.LightGray)
                    .sizeIn(100.dp, 30.dp)
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(label)
            }
        }
    ) {
        content()
    }
}

@Composable
private fun CharacterPreview(traitSelectionState: SnapshotStateMap<Trait, Boolean>) {
    Text("Character Preview")

    Row(Modifier.fillMaxWidth()) {
        Text("Personality Traits: ")
        val chunked = traitSelectionState.toList()
            .filter { it.second }
            .map { it.first.label }
            .chunked(5)
        chunked.forEach { traitChunk ->
            Column {
                traitChunk.forEach { trait ->
                    Text("- $trait")
                }
            }
        }
    }
}

@Composable
private fun InputFields(
    name: MutableState<String>,
    dna: MutableState<String>,
    dynasty: MutableState<String>,
    religion: MutableState<String>,
    culture: MutableState<String>,
    birth: MutableState<String>,
    death: MutableState<String>
) {
    TextField(
        value = name.value,
        label = { Text("Name") },
        onValueChange = {
            name.value = it
        }
    )
    TextField(
        value = dna.value,
        label = { Text("dna") },
        onValueChange = {
            dna.value = it
        }
    )
    TextField(
        value = dynasty.value,
        label = { Text("dynasty") },
        onValueChange = {
            dynasty.value = it
        }
    )
    TextField(
        value = religion.value,
        label = { Text("religion") },
        onValueChange = {
            religion.value = it
        }
    )
    TextField(
        value = culture.value,
        label = { Text("culture") },
        onValueChange = {
            culture.value = it
        }
    )
    TextField(
        value = birth.value,
        label = { Text("birth") },
        onValueChange = {
            birth.value = it
        }
    )
    TextField(
        value = death.value,
        label = { Text("death") },
        onValueChange = {
            death.value = it
        }
    )
}

@Composable
private fun CreateButton(
    name: MutableState<String>,
    dna: MutableState<String>,
    religion: MutableState<String>,
    dynasty: MutableState<String>,
    culture: MutableState<String>,
    birth: MutableState<String>,
    death: MutableState<String>,
    personalityTraitSelectionState: SnapshotStateMap<Trait, Boolean>,
    educationalTraitSelectionState: SnapshotStateMap<LeveledTrait, Int>
) {
    Button(
        onClick = {
            val characterValues = mapOf(
                "name" to name.value,
                "dna" to dna.value,
                "religion" to religion.value,
                "dynasty" to dynasty.value,
                "culture" to culture.value,
                "birth" to birth.value,
                "death" to death.value
            )

            val personalityTraits = personalityTraitSelectionState.filter { it.value }
            val educationalTraits = educationalTraitSelectionState.filterNot { it.value == 0 }

            if (validateInput(characterValues)) {
                GlobalScope.launch {
                    createNewCharacter(
                        characterValues,
                        personalityTraits,
                        educationalTraits
                    )
                }
            } else {
                NotificationsService.error("""Can not create character. Some fields were empty""")
            }
        }
    ) {
        Text("Create")
    }
}

private fun createNewCharacter(
    characterValues: Map<String, String>,
    personalityTraits: Map<Trait, Boolean>,
    educationalTraits: Map<LeveledTrait, Int>
) {

    val traits = mutableListOf<String>()
    traits.addAll(personalityTraits.map { it.key.code })
    traits.addAll(educationalTraits.map { "${it.key.code}_${it.value}" })

    val newCharacter = CK3Character(
        characterValues["name"]!!,
        characterValues["dna"]!!,
        characterValues["dynasty"]!!,
        characterValues["religion"]!!,
        characterValues["culture"]!!,
        mapOf(),
        traits,
        characterValues["birth"]!!,
        characterValues["death"]!!
    )
    if (StateHolder.characters.contains(newCharacter)) {
        NotificationsService.error("""Character with name "${newCharacter.name}" already exists""")
        return
    }

    StateHolder.characters.add(newCharacter)
    ViewManager.changeView(CHARACTER_VIEW)
    NotificationsService.notify("""New character "${newCharacter.name}" created""")
}

private fun validateInput(characterValues: Map<String, String>): Boolean {
    return characterValues.all { it.value.isNotEmpty() }
}
