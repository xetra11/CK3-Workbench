package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WorkbenchFunctions() {
    Column(Modifier.fillMaxHeight(0.8F), verticalArrangement = Arrangement.SpaceBetween) {
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
        Box(Modifier.border(3.dp, Color.Blue).preferredSize(50.dp))
    }
}
