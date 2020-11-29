package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.ScrollableRow
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.module.character.Character

/**
 * custom table component for characters
 *
 * @author Patrick C. Höfer
 */
@Composable
fun CharacterTable(
    modifier: Modifier = Modifier,
    characterState: SnapshotStateList<Character>
) {
    val scrollState = rememberScrollState(0f)

    val headerModifier = modifier.background(Color.LightGray)
    ScrollableRow(scrollState = scrollState) {
        Box(headerModifier) { Text("name") }
        Box(headerModifier) { Text("dna") }
        Box(headerModifier) { Text("dynasty") }
        Box(headerModifier) { Text("culture") }
        Box(headerModifier) { Text("religion") }
        Box(headerModifier) { Text("birth") }
        Box(headerModifier) { Text("death") }
    }

    characterState.map {
        ScrollableRow(
            scrollState = scrollState
        ) {
            Box(modifier) { Text(it.name) }
            Box(modifier) { Text(it.dna) }
            Box(modifier) { Text(it.dynasty) }
            Box(modifier) { Text(it.culture) }
            Box(modifier) { Text(it.religion) }
            Box(modifier) { Text(it.birth) }
            Box(modifier) { Text(it.death) }
        }
    }
    HorizontalScrollbar(
        modifier = Modifier.fillMaxWidth().height(15.dp),
        adapter = rememberScrollbarAdapter(scrollState)
    )
}
