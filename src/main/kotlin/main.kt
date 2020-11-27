import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
import androidx.compose.desktop.Window
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Menu
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.MenuItem
import com.github.xetra11.ck3workbench.module.character.view.CharacterModuleView

fun main() = Window(
    title = "CK3 Mod Workbench",
    menuBar = MenuBar(
        Menu("File", MenuItem("Exit", onClick = { AppManager.exit() })),
        Menu(
            "History",
            MenuItem("Characters", onClick = {}),
            MenuItem("Dynasties", onClick = {})
        ),
    )
) {
    MaterialTheme(
        colors = workbenchLightColors(),
        shapes = workBenchShapes()
    ) {
        CharacterModuleView()
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
