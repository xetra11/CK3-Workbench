import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.lightColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.module.character.CharacterTemplate
import com.github.xetra11.ck3workbench.module.character.NewCharacterForm
import com.github.xetra11.ck3workbench.module.character.ui.CharacterList

fun main() = Window(title = "CK3 Mod Workbench") {
    MaterialTheme(lightColors()) {
        Column(modifier = Modifier.border(1.dp, Color.Black).then(Modifier.fillMaxWidth())) {
            NewCharacterForm()
            CharacterList(
                listOf(
                    CharacterTemplate.DEFAULT_CHARACTER,
                    CharacterTemplate.DEFAULT_CHARACTER,
                    CharacterTemplate.DEFAULT_CHARACTER,
                )
            )
        }
    }
}
