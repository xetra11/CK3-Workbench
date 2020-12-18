package com.github.xetra11.ck3workbench.app.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun EntryView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Welcome to CK3-Workbench",
            fontSize = TextUnit.Companion.Sp(15),
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Text("On the left panel you can choose a function category", fontSize = TextUnit.Companion.Sp(10))
    }
}
