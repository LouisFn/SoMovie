package com.louisfn.somovie.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalCustomColors provides CustomColorPalette) {
        MaterialTheme(
            colors = MaterialColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

val MaterialTheme.customColors: CustomColors
    @Composable
    get() = LocalCustomColors.current

val LocalCustomColors = compositionLocalOf<CustomColors> {
    error("CustomColors not provided")
}
