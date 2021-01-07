package com.github.xetra11.ck3workbench.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

/**
 * Some components that were created throughout development
 * and might become useful
 */
object CustomComponents {

    @Composable
    fun Spoiler(
        modifier: Modifier = Modifier,
        hideInitial: Boolean = true,
        horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
        label: @Composable ColumnScope.() -> Unit,
        content: @Composable ColumnScope.() -> Unit,
    ) {
        var show by remember { mutableStateOf(!hideInitial) }
        Box(modifier.clickable { show = !show }) {
            ColumnScope.label()
        }
        val columnModifier = if (show) Modifier else Modifier.alpha(0F).size(0.dp, 0.dp)
        Column(columnModifier, horizontalAlignment = horizontalAlignment) {
            ColumnScope.content()
        }
    }
}
