package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.StateHolder
import com.github.xetra11.ck3workbench.app.ViewManager
import com.github.xetra11.ck3workbench.app.ViewManager.View.CHARACTER_VIEW
import com.github.xetra11.ck3workbench.module.character.CK3Character
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun CharacterCreateView() {
    Column(
        modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        val name = remember { mutableStateOf("") }
        val dna = remember { mutableStateOf("") }
        val religion = remember { mutableStateOf("") }
        val dynasty = remember { mutableStateOf("") }
        val culture = remember { mutableStateOf("") }
        val birth = remember { mutableStateOf("") }
        val death = remember { mutableStateOf("") }

        Text("Character Creation", fontSize = TextUnit.Sp(15), modifier = Modifier.padding(bottom = 5.dp))
        Text("In here you can create new characters", fontSize = TextUnit.Sp(10))
        Row {
            Column {
                InputFields(name, dna, dynasty, religion, culture, birth, death)
            }
        }
        CreateButton(name, dna, religion, dynasty, culture, birth, death)
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
    death: MutableState<String>
) {
    Button(
        modifier = Modifier.padding(top = 7.dp),
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

            if (validateInput(characterValues)) {
                GlobalScope.launch {
                    createNewCharacter(characterValues)
                }
            } else {
                NotificationsService.error("""Can not create character. Some fields were empty""")
            }
        }
    ) {
        Text("Create")
    }
}

private fun createNewCharacter(characterValues: Map<String, String>) {
    val newCharacter = CK3Character(
        characterValues["name"]!!,
        characterValues["dna"]!!,
        characterValues["dynasty"]!!,
        characterValues["religion"]!!,
        characterValues["culture"]!!,
        mapOf(),
        listOf(),
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
