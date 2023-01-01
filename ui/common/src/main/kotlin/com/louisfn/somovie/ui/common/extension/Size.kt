package com.louisfn.somovie.ui.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize

@Composable
fun IntSize.toDpSize() = with(LocalDensity.current) {
    DpSize(width = width.toDp(), height = height.toDp())
}

@Composable
fun Size.toDpSize() = with(LocalDensity.current) {
    DpSize(width = width.toDp(), height = height.toDp())
}

fun Size.toIntSize() = IntSize(width = width.toInt(), height = height.toInt())
