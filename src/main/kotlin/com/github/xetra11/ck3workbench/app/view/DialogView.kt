package com.github.xetra11.ck3workbench.app.view

import ProjectFileFilter
import androidx.compose.desktop.AppManager
import androidx.compose.desktop.ComposeWindow
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.xetra11.ck3workbench.app.DialogManager
import com.github.xetra11.ck3workbench.app.NotificationsService.error
import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import com.github.xetra11.ck3workbench.app.SessionManager
import com.github.xetra11.ck3workbench.app.StateHolder
import com.github.xetra11.ck3workbench.app.project.Project
import com.github.xetra11.ck3workbench.app.project.ProjectManager
import com.github.xetra11.ck3workbench.app.styles.WorkbenchButtons.BasicButton
import com.github.xetra11.ck3workbench.app.styles.WorkbenchTexts.BasicButtonText
import com.github.xetra11.ck3workbench.module.character.exporter.CharacterScriptExporter
import com.github.xetra11.ck3workbench.module.character.view.CharacterCreateView
import javax.swing.JFileChooser

@Composable
fun DialogView() {
    when (DialogManager.activeDialog()) {
        DialogManager.Dialog.CREATE_CHARACTER -> CreateCharacterDialog()
        DialogManager.Dialog.SAVE_BEFORE_EXIT -> SaveBeforeExistDialog()
        DialogManager.Dialog.CREATE_PROJECT -> CreateProjectDialog()
        DialogManager.Dialog.CHARACTER_EXPORT -> ExportCharacterDialog()
        else -> {
            // none
        }
    }
}

@Composable
private fun CreateCharacterDialog() {
    Dialog(
        onDismissRequest = { DialogManager.closeDialog() }
    ) {
        Column(
            Modifier.fillMaxSize().border(2.dp, Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            CharacterCreateView()
/*
            Button(onClick = { AppManager.focusedWindow?.close() }) {
                Text("Close")
            }
*/
        }
    }
}

@Composable
private fun SaveBeforeExistDialog() {
    AlertDialog(
        title = {
            Text("You're current project has not been saved")
        },
        text = {
            Text("Do you wanna save it before you quit?")
        },
        modifier = Modifier.size(200.dp, 200.dp),
        onDismissRequest = {
            DialogManager.closeDialog()
        },
        confirmButton = {
            BasicButton(
                onClick = {
                    saveProjectDialog(AppManager.focusedWindow!!.window)
                    DialogManager.closeDialog()
                }
            ) {
                BasicButtonText("Save project")
            }
        },
        dismissButton = {
            BasicButton(
                onClick = {
                    notify("Cancel save before exit")
                    DialogManager.closeDialog()
                }
            ) {
                BasicButtonText("Quit without save")
            }
        }
    )
}

@Composable
private fun CreateProjectDialog() {
    Dialog(
        onDismissRequest = {
            DialogManager.closeDialog()
        },
    ) {
        Column(
            Modifier.fillMaxSize().border(2.dp, Color.Blue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ProjectCreateView()
        }
    }
}

@Composable
private fun ExportCharacterDialog() {
    AlertDialog(
        title = { Text("Character Export") },
        text = { Text("Do you want to export all ${StateHolder.characters.size} character entries?") },
        onDismissRequest = { DialogManager.closeDialog() },
        confirmButton = {
            BasicButton(
                onClick = {
                    val characterScriptExporter = CharacterScriptExporter()
                    characterScriptExporter.export()
                    notify("""Characters have been exported to "character.txt"""")
                }
            ) { BasicButtonText("Export") }
        },
        dismissButton = {
            BasicButton(onClick = { AppManager.focusedWindow?.close() }) { BasicButtonText("Cancel") }
        }
    )
}

private fun saveProjectDialog(window: ComposeWindow) {
    val fileChooser = JFileChooser()
    fileChooser.addChoosableFileFilter(ProjectFileFilter())

    when (fileChooser.showSaveDialog(window)) {
        JFileChooser.APPROVE_OPTION -> {
            notify("Save project to file")
            val projectManager = ProjectManager()
            val sessionManager = SessionManager()
            val projectFile = fileChooser.selectedFile
            val projectToSave = Project()

            projectToSave.location = projectFile.absolutePath + ".wbp"
            projectManager.saveProject(projectToSave)
            sessionManager.activateProject(projectToSave)
        }
        JFileChooser.CANCEL_OPTION -> {
            notify("Cancel saving project before exit")
            notify("Project has not been saved before exit")
        }
    }
    error("Reached end of shutdown logic for some reason")
}
