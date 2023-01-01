package com.louisfn.somovie.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Suppress("ModifierMissing")
@Composable
fun RowSpacer(space: Dp) {
    Spacer(modifier = Modifier.width(space))
}

@Suppress("ModifierMissing")
@Composable
fun ColumnSpacer(space: Dp) {
    Spacer(modifier = Modifier.height(space))
}
