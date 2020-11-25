package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.module.character.Character

/**
 * The list component to show all character in
 *
 * @author Patrick C. Höfer
 */
@Composable
fun CharacterList(
    characterState: SnapshotStateList<Character>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val boxModifier = Modifier.border(1.dp, Color.Black)
            .then(Modifier.padding(horizontal = 2.dp, vertical = 4.dp))
            .then(Modifier.width(100.dp))

        CharacterTable(
            boxModifier,
            characterState
        )

    }
}
