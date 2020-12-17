package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.runtime.Composable
import com.github.xetra11.ck3workbench.app.ViewManager

@Composable
fun CurrentView() {
    when (ViewManager.currentView.value) {
        ViewManager.View.CHARACTER_VIEW -> CharacterModuleView()
        ViewManager.View.OTHER_VIEW -> DynastieModuleView()
    }
}
