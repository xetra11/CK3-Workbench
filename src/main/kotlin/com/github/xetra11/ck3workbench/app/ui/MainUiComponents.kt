package com.github.xetra11.ck3workbench.app.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object MainUiComponents {
    @Composable
    fun NotificationPanelRow(
        content: @Composable RowScope.() -> Unit
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }

    @Composable
    fun MainLayoutRow(
        view: @Composable BoxScope.() -> Unit
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.975F)
                .border(2.dp, Color.LightGray)
        ) {
            Box(
                Modifier
                    .border(2.dp, Color.LightGray)
                    .fillMaxWidth(0.03F)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                WorkbenchPanel.Functions()
            }
            Box(
                Modifier
                    .border(2.dp, Color.LightGray)
                    .fillMaxWidth(),
                content = view
            )
        }
    }
}
