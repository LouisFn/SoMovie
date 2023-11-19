package com.louisfn.somovie.ui.common.extension

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun PaddingValues.copy(
    start: Dp? = null,
    top: Dp? = null,
    end: Dp? = null,
    bottom: Dp? = null,
): PaddingValues =
    remember(this) {
        derivedStateOf {
            PaddingValues(
                start = start ?: calculateStartPadding(LayoutDirection.Ltr),
                top = top ?: calculateTopPadding(),
                end = end ?: calculateEndPadding(LayoutDirection.Ltr),
                bottom = bottom ?: calculateBottomPadding(),
            )
        }
    }.value

val PaddingValues.start
    get() = calculateStartPadding(LayoutDirection.Ltr)

val PaddingValues.end
    get() = calculateEndPadding(LayoutDirection.Ltr)

val PaddingValues.top
    get() = calculateTopPadding()

val PaddingValues.bottom
    get() = calculateBottomPadding()

operator fun PaddingValues.plus(value: PaddingValues) =
    PaddingValues(
        start = start + value.start,
        top = top + value.top,
        bottom = bottom + value.bottom,
        end = end + value.end,
    )
