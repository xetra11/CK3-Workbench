import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import com.github.xetra11.ck3workbench.app.notifications.NotificationPanel
import com.github.xetra11.ck3workbench.app.ui.MainUiComponents
import com.github.xetra11.ck3workbench.module.character.importer.CharacterScriptImporter
import com.github.xetra11.ck3workbench.module.character.view.CurrentView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.FileDialog
import java.io.File
import javax.swing.SwingUtilities.invokeLater

fun main() = invokeLater {
    lateinit var window: AppWindow
    val hasAlert = mutableStateOf(false)
    val windowSize = mutableStateOf(IntSize.Zero)

    window = AppWindow(
        title = "CK3 Mod Workbench v0.0.1-a",
        menuBar = MenuBar(
            Menu(
                "File",
                MenuItem("Exit", onClick = { AppManager.exit() })
            ),
            Menu(
                "Characters",
                MenuItem(
                    "Import Characters",
                    onClick = {
                        val file = openScriptFile(window)
                        val characterScriptImporter = CharacterScriptImporter()
                        characterScriptImporter.importCharactersScript(file)
                    }
                ),
                MenuItem("Dynasties", onClick = {})
            ),
        )
    )

    window.show {
        window.events.onResize = { windowSize.value = it }
        MaterialTheme(
            colors = workbenchLightColors(),
            shapes = workBenchShapes()
        ) {
            Column(Modifier.fillMaxSize()) {
                MainUiComponents.MainLayoutRow { CurrentView() }
                MainUiComponents.NotificationPanelRow { NotificationPanel() }
            }
        }
    }
}

private fun openScriptFile(window: AppWindow): MutableState<File> {
    val file = mutableStateOf(File(""))

    val fileDialog = FileDialog(window.window)
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
