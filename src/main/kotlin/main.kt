import androidx.compose.desktop.AppManager
import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.KeyStroke
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import com.github.xetra11.ck3workbench.app.AppInitializer
import com.github.xetra11.ck3workbench.app.AppShutdownService
import com.github.xetra11.ck3workbench.app.FileSystemHelper
import com.github.xetra11.ck3workbench.app.NotificationsService.notify
import com.github.xetra11.ck3workbench.app.SessionHolder
import com.github.xetra11.ck3workbench.app.SessionManager
import com.github.xetra11.ck3workbench.app.ViewManager
import com.github.xetra11.ck3workbench.app.dialog.DialogManager
import com.github.xetra11.ck3workbench.app.project.ProjectManager
import com.github.xetra11.ck3workbench.app.ui.MainUiComponents
import com.github.xetra11.ck3workbench.app.view.DialogView
import com.github.xetra11.ck3workbench.module.character.exporter.CharacterScriptExporter
import com.github.xetra11.ck3workbench.module.character.view.CurrentMainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

fun main() {
    CoroutineScope(Dispatchers.Default).launch {
        val time = measureTimeMillis {
            initializeApp()
        }
        notify("Workbench initialized in $time ms")
    }

    Window(
        title = "CK3 Mod Workbench",
        menuBar = AppMenu()
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
                MainUiComponents.NotificationPanel()
            }
        }
    }
}

private fun AppMenu(): MenuBar {
    val fileSystemHelper = FileSystemHelper()
    return MenuBar(
        Menu(
            "File",
            MenuItem(
                "New Project",
                shortcut = KeyStroke(Key.P),
                onClick = {
                    DialogManager.openDialog(DialogManager.Dialog.CREATE_PROJECT)
                }
            ),
            MenuItem(
                "Open Project",
                shortcut = KeyStroke(Key.O),
                onClick = { fileSystemHelper.loadProjectFile(AppManager.focusedWindow!!.window) }
            ),
            MenuItem(
                "Save Project",
                shortcut = KeyStroke(Key.S),
                onClick = { saveProject() }
            ),
            MenuItem(
                "Save Project as",
                shortcut = KeyStroke(Key.W),
                onClick = { fileSystemHelper.saveProjectAs(AppManager.focusedWindow!!.window) }
            ),
            MenuItem(
                "Exit",
                shortcut = KeyStroke(Key.X),
                onClick = {
                    val appShutdownService = AppShutdownService()
                    appShutdownService.shutdown()
                    AppManager.exit()
                }
            )
        ),
        Menu(
            "Characters",
            MenuItem(
                "Create Character",
                shortcut = KeyStroke(Key.C),
                onClick = { ViewManager.changeView(ViewManager.View.CHARACTER_CREATE_VIEW) }
            ),
/*
            MenuItem(
                "Import Characters",
                shortcut = KeyStroke(Key.I),
                onClick = {
                    val file = fileSystemHelper.openScriptFile(AppManager.focusedWindow!!.window)
                    val characterScriptImporter = CharacterScriptImporter()
                    characterScriptImporter.importCharactersScript(file)
                }
            ),
*/
            MenuItem(
                "Export Characters",
                shortcut = KeyStroke(Key.E),
                onClick = {
                    val characterScriptExporter = CharacterScriptExporter()
                    characterScriptExporter.export()
                    notify("""Characters have been exported to "character.txt"""")
                    // DialogManager.openDialog(DialogManager.Dialog.CHARACTER_EXPORT)
                }
            )
        )
    )
}

private suspend fun initializeApp() {
    coroutineScope {
        val sessionManager = SessionManager()
        val projectManager = ProjectManager()
        val appInitializer = AppInitializer(sessionManager, projectManager)
        launch { appInitializer.initialize() }
        launch { initializeEvents() }
    }
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
