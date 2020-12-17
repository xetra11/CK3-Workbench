package com.github.xetra11.ck3workbench.app

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.github.xetra11.ck3workbench.module.character.view.CharacterModuleView
import kotlin.reflect.KFunction0

object ViewManager {
    val currentView = mutableStateOf(View.CHARACTER_VIEW)

    enum class View {
        CHARACTER_VIEW,
        OTHER_VIEW
    }

}
