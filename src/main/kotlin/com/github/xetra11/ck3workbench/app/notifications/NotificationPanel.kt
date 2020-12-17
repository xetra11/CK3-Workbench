package com.github.xetra11.ck3workbench.module.character.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.NotificationsService

@Composable
fun NotificationPanel() {
    Row(
        Modifier.border(3.dp, Color.Yellow)
            .fillMaxSize()
            .padding(end = 10.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val latestMessage = NotificationsService.latestMessage()
        when (latestMessage.type) {
            NotificationsService.NotificationType.ERROR -> Text("ERROR: ${latestMessage.message}", color = Color.Red)
            NotificationsService.NotificationType.WARNING -> Text("WARNING: ${latestMessage.message}")
            NotificationsService.NotificationType.NOTIFICATION -> Text(latestMessage.message)
        }
    }
}
