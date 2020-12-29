package com.github.xetra11.ck3workbench.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.ViewManager

object WorkbenchPanel {
    private const val PANZER_IMG_PATH = "icons/thepinkpanzer"
    private val boxModifier = Modifier.preferredSize(50.dp)
    val squareImage = imageFromResource("$PANZER_IMG_PATH/BG_Square_Brown.png")
    val crownImage = imageFromResource("$PANZER_IMG_PATH/Piece_Crown_Rusty.png")
    val bloodImage = imageFromResource("$PANZER_IMG_PATH/Piece_Blood.png")
    val plusIconImage = imageFromResource("icons/plus.png")

    @Composable
    fun Functions() {

        Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Top) {
            characterListIcon()
            addCharacterIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
            emptyIcon()
        }
    }

    @Composable
    private fun emptyIcon() {
        Box(
            boxModifier,
            contentAlignment = Alignment.Center,
        ) {
            Image(squareImage, alpha = 0.2F)
        }
    }

    @Composable
    private fun dynastyListIcon() {
        Box(
            boxModifier.clickable(onClick = { ViewManager.changeView(ViewManager.View.DYNASTY_VIEW) }),
            contentAlignment = Alignment.Center,
        ) {
            Image(squareImage)
            Image(bloodImage, modifier = Modifier.fillMaxSize(0.7F))
        }
    }

    @Composable
    private fun addCharacterIcon() {
        Box(
            boxModifier.clickable(onClick = { ViewManager.changeView(ViewManager.View.CHARACTER_CREATE_VIEW) }),
            contentAlignment = Alignment.Center,
        ) {
            Image(squareImage)
            Image(crownImage, modifier = Modifier.fillMaxSize(0.7F))
            Image(plusIconImage, modifier = Modifier.fillMaxSize(0.4F).align(Alignment.BottomEnd))
        }
    }

    @Composable
    private fun characterListIcon() {
        Box(
            boxModifier.clickable(onClick = { ViewManager.changeView(ViewManager.View.CHARACTER_VIEW) }),
            contentAlignment = Alignment.Center,
        ) {
            Image(squareImage)
            Image(crownImage, modifier = Modifier.fillMaxSize(0.7F))
        }
    }
}
