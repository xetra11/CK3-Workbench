import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import com.github.xetra11.ck3workbench.ScriptValidator.ValidationError
import com.github.xetra11.ck3workbench.module.character.Character
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import com.github.xetra11.ck3workbench.module.character.importer.CharacterScriptImporter
import com.github.xetra11.ck3workbench.module.character.importer.CharacterScriptReader
import com.github.xetra11.ck3workbench.module.character.importer.ScriptValidatorFactory
import com.github.xetra11.ck3workbench.module.character.view.CharacterModuleView
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.FileDialog
import java.io.File
import javax.swing.SwingUtilities.invokeLater

fun main() = invokeLater {
    lateinit var window: AppWindow
    val hasAlert = mutableStateOf(false)
    val validationErrors = mutableStateListOf<ValidationError>()

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
                MenuItem(
                    "Import Characters",
                    onClick = {
                        val file = openScriptFile(window)
                        val characterScriptImporter = CharacterScriptImporter()
                        characterScriptImporter
                            .importCharactersScript(file, hasAlert, characterState)
                    }
                ),
                MenuItem("Dynasties", onClick = {})
            ),
        )
    )

    window.show {
        MaterialTheme(
            colors = workbenchLightColors(),
            shapes = workBenchShapes()
        ) {
            if (hasAlert.value) {
                CharacterValidationErrorAlert(hasAlert, validationErrors)
            }
            CharacterModuleView(characterState)
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

@Composable
private fun CharacterValidationErrorAlert(
    alertState: MutableState<Boolean>,
    validationErrors: SnapshotStateList<ValidationError>
) {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Validation Error")
            validationErrors.forEach { error ->
                Text("- ${error.reason}")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                modifier = Modifier.background(Color.LightGray),
                onClick = { alertState.value = false }
            ) {
                Text("Ok")
            }
        }
    }
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
