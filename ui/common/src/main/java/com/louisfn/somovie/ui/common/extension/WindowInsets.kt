package com.louisfn.somovie.ui.common.extension

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity

val WindowInsets.top
    @Composable
    get() = getTop(LocalDensity.current)

val WindowInsets.bottom
    @Composable
    get() = getBottom(LocalDensity.current)
