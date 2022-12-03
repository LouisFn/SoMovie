package com.louisfn.somovie.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = MaterialTheme.colors.surface,
    actionColor: Color = MaterialTheme.colors.secondary,
    elevation: Dp = 6.dp
) {
    val actionLabel = snackbarData.actionLabel
    val actionComposable: (@Composable () -> Unit)? =
        if (actionLabel != null) {
            @Composable {
                TextButton(
                    onClick = { snackbarData.performAction() },
                    content = { Text(text = actionLabel, color = actionColor) }
                )
            }
        } else {
            null
        }
    Snackbar(
        modifier = modifier.padding(12.dp),
        content = { Text(snackbarData.message) },
        action = actionComposable,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    )
}
