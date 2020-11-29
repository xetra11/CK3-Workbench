import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
import androidx.compose.desktop.AppWindowAmbient
import androidx.compose.desktop.Window
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientWindowManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import com.github.xetra11.ck3workbench.module.character.Character
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import com.github.xetra11.ck3workbench.module.character.importer.CharacterScriptReader
import com.github.xetra11.ck3workbench.module.character.view.CharacterModuleView
import java.awt.FileDialog
import java.io.File
import javax.swing.SwingUtilities.invokeLater

fun main() = invokeLater {
    lateinit var window: AppWindow

    val characterState: SnapshotStateList<Character> =
        mutableStateListOf(
            CharacterTemplate.DEFAULT_CHARACTER,
            CharacterTemplate.DEFAULT_CHARACTER,
            CharacterTemplate.DEFAULT_CHARACTER
    )

    window = AppWindow(
        title = "CK3 Mod Workbench",
        menuBar = MenuBar(
            Menu("File", MenuItem("Exit", onClick = { AppManager.exit() })),
            Menu(
                "Characters",
                MenuItem("Import characters", onClick = {
                    val file = mutableStateOf(File(""))

                    val fileDialog = FileDialog(window.window)
                    fileDialog.mode = FileDialog.LOAD
                    fileDialog.isVisible = true
                    file.value = File(fileDialog.directory + fileDialog.file)

                    val characterScriptReader = CharacterScriptReader()
                    val character = characterScriptReader.readCharacterScript(file.value.absoluteFile)
                    character?.let { characterState.add(it) }
                }),
                MenuItem("Dynasties", onClick = {})
            ),
        )
    )

    window.show {
        MaterialTheme(
            colors = workbenchLightColors(),
            shapes = workBenchShapes()
        ) {
            CharacterModuleView(characterState)
        }
    }
}

fun workbenchLightColors(
    primary: Color = Color.LightGray,
    primaryVariant: Color = Color.Red,
    secondary: Color = Color.Red,
    secondaryVariant: Color = Color.Red,
    background: Color = Color.DarkGray,
    surface: Color = Color.Red,
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
