package com.github.xetra11.ck3workbench.module.character.view

import androidx.compose.runtime.Composable
import com.github.xetra11.ck3workbench.app.ViewManager
import com.github.xetra11.ck3workbench.app.view.EntryView

@Composable
fun CurrentMainView() {
    when (ViewManager.currentMainView()) {
        ViewManager.View.ENTRY_VIEW -> EntryView()
        ViewManager.View.DYNASTY_VIEW -> DynastieModuleView()
        ViewManager.View.CHARACTER_VIEW -> CharacterModuleView()
        ViewManager.View.CHARACTER_CREATE_VIEW -> CharacterFactoryView()
    }
}
