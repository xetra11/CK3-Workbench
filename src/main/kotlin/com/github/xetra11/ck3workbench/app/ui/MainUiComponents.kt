package com.github.xetra11.ck3workbench.app.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.xetra11.ck3workbench.app.NotificationsService
import com.github.xetra11.ck3workbench.app.SessionHolder

object MainUiComponents {
    @Composable
    fun NotificationPanel() {
        Row(
            Modifier
                .fillMaxSize()
                .border(2.dp, Color.LightGray)
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Active Project: ${SessionHolder.activeSession.value.activeProject?.location}")
            val latestMessage = NotificationsService.latestMessage()
            when (latestMessage.type) {
                NotificationsService.NotificationType.ERROR -> Text(
                    "ERROR: ${latestMessage.message}",
                    color = Color.Red
                )
                NotificationsService.NotificationType.WARNING -> Text("WARNING: ${latestMessage.message}")
                NotificationsService.NotificationType.NOTIFICATION -> Text(latestMessage.message)
            }
        }
    }

    @Composable
    fun MainLayoutRow(
        view: @Composable BoxScope.() -> Unit
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.975F)
        ) {
            Box(
                Modifier
                    .border(2.dp, Color.LightGray)
                    .preferredWidth(50.dp)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                WorkbenchPanel.Functions()
            }
            Box(
                Modifier.fillMaxWidth(),
                content = view
            )
        }
    }
}
