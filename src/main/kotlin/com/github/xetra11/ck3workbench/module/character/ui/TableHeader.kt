package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Custom Table Header
 *
 * @author Patrick C. Höfer
 */
@Composable
fun TableHeader(
    modifier: Modifier = Modifier,
    items: List<String>
) {
    Row {
        Box(modifier) { Text("name") }
        Box(modifier) { Text("dna") }
        Box(modifier) { Text("dynasty") }
        Box(modifier) { Text("culture") }
        Box(modifier) { Text("religion") }
        Box(modifier) { Text("birth") }
        Box(modifier) { Text("death") }
    }
}
