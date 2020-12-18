package com.github.xetra11.ck3workbench.app

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.xetra11.ck3workbench.module.character.CK3Character
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate

object StateManager {
    val characters: SnapshotStateList<CK3Character> = mutableStateListOf()
}
