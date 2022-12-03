package com.louisfn.somovie.ui.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun Dp.toPx() = with(LocalDensity.current) { toPx() }

@Composable
fun Dp.roundToPx() = with(LocalDensity.current) { roundToPx() }
