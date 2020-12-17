package com.github.xetra11.ck3workbench.app.ui

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.ViewManager

@Composable
fun WorkbenchPanel() {
    val boxModifier = Modifier.preferredSize(50.dp)
    val panzerImagesPath = "icons/thepinkpanzer"
    val squareImage = imageResource("$panzerImagesPath/BG_Square_Brown.png")
    val crownImage = imageResource("$panzerImagesPath/Piece_Crown_Rusty.png")
    val bloodImage = imageResource("$panzerImagesPath/Piece_Blood.png")

    Column(Modifier.fillMaxHeight(0.8F), verticalArrangement = Arrangement.SpaceBetween) {
        Box(
            boxModifier.clickable(onClick = { ViewManager.currentView.value = ViewManager.View.CHARACTER_VIEW }),
            contentAlignment = Alignment.Companion.Center,
        ) {
            Image(squareImage)
            Image(crownImage, modifier = Modifier.fillMaxSize(0.7F))
        }
        Box(
            boxModifier.clickable(onClick = { ViewManager.currentView.value = ViewManager.View.OTHER_VIEW }),
            contentAlignment = Alignment.Companion.Center,
        ) {
            Image(squareImage)
            Image(bloodImage, modifier = Modifier.fillMaxSize(0.7F))
        }
        Box(
            boxModifier
                .clickable(
                    onClick = { NotificationsService.notify("CLICK 3") }
                )
        ) {
            Image(squareImage)
        }
        Box(
            boxModifier
                .clickable(
                    onClick = { NotificationsService.notify("CLICK") }
                )
        ) {
            Image(squareImage)
        }
        Box(
            boxModifier
                .clickable(
                    onClick = { NotificationsService.notify("CLICK") }
                )
        ) {
            Image(squareImage)
        }
    }
}
