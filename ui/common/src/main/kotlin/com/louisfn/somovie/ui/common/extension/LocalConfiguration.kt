package com.louisfn.somovie.ui.common.extension

import android.content.res.Configuration
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

val Configuration.screenWidth: Float
    @Composable
    get() = with(LocalDensity.current) { screenWidthDp.dp.toPx() }

val Configuration.screenHeight: Float
    @Composable
    get() = with(LocalDensity.current) { screenHeightDp.dp.toPx() }

val Configuration.screenHeightWithInset: Float
    @Composable
    get() = with(LocalDensity.current) {
        screenHeight +
            WindowInsets.statusBars.getTop(this) +
            WindowInsets.navigationBars.getBottom(this)
    }
