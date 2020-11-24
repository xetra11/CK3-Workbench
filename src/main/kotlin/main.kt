import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import com.github.xetra11.ck3workbench.module.character.view.CharacterModuleView

fun main() = Window(title = "CK3 Mod Workbench") {
    MaterialTheme(lightColors()) {
        CharacterModuleView()
    }
}
