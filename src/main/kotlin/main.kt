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
import com.github.xetra11.ck3workbench.app.DialogManager
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.Project
import com.github.xetra11.ck3workbench.app.ProjectManager
import com.github.xetra11.ck3workbench.app.SessionHolder
import com.github.xetra11.ck3workbench.app.SessionManager
import com.github.xetra11.ck3workbench.app.StateHolder
import com.github.xetra11.ck3workbench.app.notifications.NotificationPanel
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
import javax.swing.filechooser.FileFilter

fun main() {
    val sessionManager = SessionManager()
    sessionManager.load()
    initializeEvents(sessionManager)
    loadProjectFromSession()

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
                MenuItem("Exit", onClick = { AppManager.exit() })
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

private fun hasNoActiveProject() = SessionHolder.activeSession?.activeProject == null

/**
 * Loads the project from the sessions active project
 *
 * @return true if the project was loaded successfully and false if not
 */
private fun loadProjectFromSession() {
    NotificationsService.notify("Load project from session")
    SessionHolder.activeSession?.let { session ->
        session.activeProject?.let { project ->
            // initializeCharacters(project)
        } ?: run {
            NotificationsService.warn("No project was found on session")
        }
    } ?: run {
        NotificationsService.error("No active session")
    }
}

private fun initializeCharacters(project: Project): Boolean {
    NotificationsService.notify("Load characters")
    StateHolder.characters.clear()
    return StateHolder.characters.addAll(project.state.characters)
}

private fun initializeEvents(sessionManager: SessionManager) {
    AppManager.setEvents(
        onAppExit = {
            sessionManager.exit()
        }
    )
}

private fun loadProjectFile(window: ComposeWindow) {
    val file = mutableStateOf(File(""))

    val fileChooser = JFileChooser()
    fileChooser.addChoosableFileFilter(ProjectFileFilter())

    when (fileChooser.showOpenDialog(window)) {
        APPROVE_OPTION -> {
            val projectManager = ProjectManager()
            val projectFile = fileChooser.selectedFile
            val projectFromFile = Json.decodeFromString<Project>(projectFile.readText())
            TODO("Project has to be loaded")
        }
        CANCEL_OPTION -> {
            NotificationsService.warn("Cancel project file opening")
        }
    }
}

class ProjectFileFilter : FileFilter() {
    override fun accept(file: File?): Boolean {
        file?.let { f ->
            if (f.isDirectory) {
                return true
            }
            return f.extension == "wbp"
        } ?: run {
            NotificationsService.error("No file given")
        }
        return false
    }

    override fun getDescription(): String {
        return "Workbench Project File"
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
