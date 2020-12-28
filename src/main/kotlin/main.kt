import androidx.compose.desktop.AppManager
import androidx.compose.desktop.ComposeWindow
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import com.github.xetra11.ck3workbench.app.AppInitializer
import com.github.xetra11.ck3workbench.app.AppShutdownService
import com.github.xetra11.ck3workbench.app.DialogManager
import com.github.xetra11.ck3workbench.app.NotificationsService.error
import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import com.github.xetra11.ck3workbench.app.Project
import com.github.xetra11.ck3workbench.app.ProjectManager
import com.github.xetra11.ck3workbench.app.SessionHolder
import com.github.xetra11.ck3workbench.app.SessionManager
import com.github.xetra11.ck3workbench.app.notifications.NotificationPanel
import com.github.xetra11.ck3workbench.app.loadProject
import com.github.xetra11.ck3workbench.app.ui.MainUiComponents
import com.github.xetra11.ck3workbench.app.view.DialogView
import com.github.xetra11.ck3workbench.module.character.importer.CharacterScriptImporter
import com.github.xetra11.ck3workbench.module.character.view.CurrentMainView
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.FileDialog
import java.io.File
import javax.swing.JFileChooser
import javax.swing.JFileChooser.APPROVE_OPTION
import javax.swing.JFileChooser.CANCEL_OPTION

fun main() {
    initializeApp()

    Window(
        title = "CK3 Mod Workbench",
        menuBar = MenuBar(
            Menu(
                "File",
                MenuItem(
                    "New Project",
                    onClick = {
                        DialogManager.openDialog(DialogManager.Dialog.CREATE_PROJECT)
                    }
                ),
                MenuItem(
                    "Open Project",
                    onClick = { loadProjectFile(AppManager.focusedWindow!!.window) }
                ),
                MenuItem(
                    "Save Project",
                    onClick = { saveProject() }
                ),
                MenuItem(
                    "Save Project as",
                    onClick = { saveProjectAs(AppManager.focusedWindow!!.window) }
                ),
                MenuItem("Exit", onClick = {
                    val appShutdownService = AppShutdownService()
                    appShutdownService.shutdown()
                    AppManager.exit()
                })
            ),
            Menu(
                "Characters",
                MenuItem(
                    "Create Character",
                    onClick = { DialogManager.openDialog(DialogManager.Dialog.CREATE_CHARACTER) }
                ),
                MenuItem(
                    "Import Characters",
                    onClick = {
                        val file = openScriptFile(AppManager.focusedWindow!!.window)
                        val characterScriptImporter = CharacterScriptImporter()
                        characterScriptImporter.importCharactersScript(file)
                    }
                ),
                MenuItem(
                    "Export Character Scripts",
                    onClick = {
                        DialogManager.openDialog(DialogManager.Dialog.CHARACTER_EXPORT)
                    }
                ),
            ),
            Menu("Dynasties")
        )
    ) {
        if (hasNoActiveProject()) {
            if (DialogManager.activeDialog() == DialogManager.Dialog.NO_DIALOG) {
                DialogManager.openDialog(DialogManager.Dialog.CREATE_PROJECT)
            }
        }
        MaterialTheme(
            colors = workbenchLightColors(),
            shapes = workBenchShapes()
        ) {
            DialogView() // is only rendered when dialog is toggled true
            Column(Modifier.fillMaxSize()) {
                MainUiComponents.MainLayoutRow { CurrentMainView() }
                MainUiComponents.NotificationPanelRow { NotificationPanel() }
            }
        }
    }
}

private fun initializeApp() {
    val sessionManager = SessionManager()
    val projectManager = ProjectManager()
    val appInitializer = AppInitializer(sessionManager, projectManager)
    appInitializer.initialize()
    initializeEvents()
}

private fun hasNoActiveProject() = SessionHolder.activeSession.value.activeProject == null

private fun initializeEvents() {
    AppManager.setEvents(
        onAppExit = {
            AppShutdownService().shutdown()
        }
    )
}

private fun saveProject() {
    val projectManager = ProjectManager()
    projectManager.saveCurrentProject()
}

private fun saveProjectAs(window: ComposeWindow) {
    val fileChooser = JFileChooser()
    fileChooser.addChoosableFileFilter(ProjectFileFilter())

    when (fileChooser.showSaveDialog(window)) {
        APPROVE_OPTION -> {
            notify("Save project to file")
            val projectManager = ProjectManager()
            val sessionManager = SessionManager()
            val projectFile = fileChooser.selectedFile
            val currentProject = SessionHolder.activeSession.value.activeProject?.loadProject()

            currentProject?.let { project ->
                project.location = projectFile.absolutePath + ".wbp"
                projectManager.saveProject(currentProject)
                sessionManager.activateProject(project)
            } ?: run {
                error("No current project was available")
            }
        }
        CANCEL_OPTION -> {
            // warn("Cancel project file opening")
        }
    }
}

private fun loadProjectFile(window: ComposeWindow) {
    val fileChooser = JFileChooser()
    fileChooser.addChoosableFileFilter(ProjectFileFilter())

    when (fileChooser.showOpenDialog(window)) {
        APPROVE_OPTION -> {
            notify("Load project from file")
            val projectManager = ProjectManager()
            val sessionManager = SessionManager()
            val projectFile = fileChooser.selectedFile
            val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())
            projectManager.load(projectFromFile)
            sessionManager.activateProject(projectFromFile)
        }
        CANCEL_OPTION -> {
            // warn("Cancel project file opening")
        }
    }
}

private fun openScriptFile(window: ComposeWindow): MutableState<File> {
    val file = mutableStateOf(File(""))

    val fileDialog = FileDialog(window)
    fileDialog.mode = FileDialog.LOAD
    fileDialog.isVisible = true
    file.value = File(fileDialog.directory + fileDialog.file)
    return file
}

private fun LOG(): Logger {
    return LoggerFactory.getLogger("Main")
}

fun workbenchLightColors(
    primary: Color = Color.LightGray,
    primaryVariant: Color = Color.Red,
    secondary: Color = Color.Red,
    secondaryVariant: Color = Color.Red,
    background: Color = Color.DarkGray,
    surface: Color = Color.White,
    error: Color = Color(0xFFB00020),
    onPrimary: Color = Color.Black,
    onSecondary: Color = Color.Black,
    onBackground: Color = Color.Black,
    onSurface: Color = Color.Black,
    onError: Color = Color.White
): Colors = Colors(
    primary,
    primaryVariant,
    secondary,
    secondaryVariant,
    background,
    surface,
    error,
    onPrimary,
    onSecondary,
    onBackground,
    onSurface,
    onError,
    true
)

fun workBenchShapes() = Shapes(
    CutCornerShape(0.dp),
    CutCornerShape(0.dp),
    CutCornerShape(0.dp)
)
