package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.NotificationsService

@Composable
fun WorkbenchFunctions() {
    Column(Modifier.fillMaxHeight(0.8F), verticalArrangement = Arrangement.SpaceBetween) {
        val squareImage = imageResource("icons/thepinkpanzer/BG_Square_Brown.png")
        Box(Modifier.preferredSize(50.dp)
            .clickable(
                onClick = { NotificationsService.notify("CLICK") }
            )) {
            Image(squareImage)
        }
        Box(Modifier.preferredSize(50.dp)
            .clickable(
                onClick = { NotificationsService.notify("CLICK 2") }
            )) {
            Image(squareImage)
        }
        Box(Modifier.preferredSize(50.dp)
            .clickable(
                onClick = { NotificationsService.notify("CLICK 3") }
            )) {
            Image(squareImage)
        }
        Box(Modifier.preferredSize(50.dp)
            .clickable(
                onClick = { NotificationsService.notify("CLICK") }
            )) {
            Image(squareImage)
        }
        Box(Modifier.preferredSize(50.dp)
            .clickable(
                onClick = { NotificationsService.notify("CLICK") }
            )) {
            Image(squareImage)
        }
    }
}
