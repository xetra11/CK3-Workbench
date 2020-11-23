package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.xetra11.ck3workbench.module.character.Character

/**
 * custom table component for characters
 *
 * @author Patrick C. Höfer
 */
@Composable
fun CharacterTable(
    modifier: Modifier = Modifier,
    characters: List<Character>
) {
    TableHeader(
        modifier.then(Modifier.background(Color.LightGray)),
        items = listOf(
            "name",
            "dna",
            "dynasty",
            "culture",
            "religion",
            "birth",
            "death"
        )
    )

    characters.map {
        Row {
            Box(modifier) { Text(it.name) }
            Box(modifier) { Text(it.dna) }
            Box(modifier) { Text(it.dynasty) }
            Box(modifier) { Text(it.culture) }
            Box(modifier) { Text(it.religion) }
            Box(modifier) { Text(it.birth) }
            Box(modifier) { Text(it.death) }
        }
    }
}
