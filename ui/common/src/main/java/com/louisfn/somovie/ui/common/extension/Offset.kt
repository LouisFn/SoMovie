package com.louisfn.somovie.ui.common.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset

fun Offset.toIntOffset() = IntOffset(
    this.x.toInt(),
    this.y.toInt()
)
