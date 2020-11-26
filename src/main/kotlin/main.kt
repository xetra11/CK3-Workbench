import androidx.compose.desktop.AppManager
import androidx.compose.desktop.AppWindow
import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
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
    MaterialTheme(lightColors()) {
        CharacterModuleView()
    }
}
