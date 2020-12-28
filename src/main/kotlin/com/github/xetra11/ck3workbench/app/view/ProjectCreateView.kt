package com.github.xetra11.ck3workbench.app.view

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
import com.github.xetra11.ck3workbench.app.Project
import com.github.xetra11.ck3workbench.app.SessionHolder
import com.github.xetra11.ck3workbench.app.SessionProject

@Composable
fun ProjectCreateView() {
    Column(
        modifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        val name = remember { mutableStateOf("") }
        val description = remember { mutableStateOf("") }

        Text("Start New Project", fontSize = TextUnit.Sp(15), modifier = Modifier.padding(bottom = 5.dp))
        Text("Start a new workbench project", fontSize = TextUnit.Sp(10))
        Row {
            Column {
                TextField(
                    value = name.value,
                    label = { Text("Project Name") },
                    onValueChange = {
                        name.value = it
                    }
                )
                TextField(
                    value = description.value,
                    label = { Text("Project Description") },
                    onValueChange = {
                        description.value = it
                    }
                )
            }
        }
        StartButton(name, description)
    }
}

@Composable
private fun StartButton(
    name: MutableState<String>,
    description: MutableState<String>
) {
    Button(
        modifier = Modifier.padding(top = 7.dp),
        onClick = {
            val newProject = Project(
                name.value,
                description.value
            )
            SessionHolder.activeSession.value.activeProject = SessionProject("")
            NotificationsService.notify("New project has been set to active project")
        }
    ) {
        Text("Start")
    }
}
