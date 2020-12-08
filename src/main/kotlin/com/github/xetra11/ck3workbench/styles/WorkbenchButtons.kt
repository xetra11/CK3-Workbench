package com.github.xetra11.ck3workbench.styles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonConstants
import androidx.compose.material.ButtonElevation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/*
 * A collection of compose button
 */
object WorkbenchButtons {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BasicButton(
        onClick: () -> Unit,
        modifier: Modifier = Modifier.preferredSize(70.dp, 25.dp),
        enabled: Boolean = true,
        interactionState: InteractionState = remember { InteractionState() },
        elevation: ButtonElevation? = ButtonConstants.defaultElevation(),
        shape: Shape = MaterialTheme.shapes.small,
        border: BorderStroke? = null,
        colors: ButtonColors = ButtonConstants.defaultButtonColors(),
        contentPadding: PaddingValues = ButtonConstants.DefaultContentPadding,
        content: @Composable RowScope.() -> Unit
    ) {
        Button(
            onClick,
            modifier,
            enabled,
            interactionState,
            elevation,
            shape,
            border,
            colors,
            contentPadding,
            content
        )
    }
}
