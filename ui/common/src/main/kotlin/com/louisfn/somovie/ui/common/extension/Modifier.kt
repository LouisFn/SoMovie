package com.louisfn.somovie.ui.common.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.clickable(withRipple: Boolean, onClick: () -> Unit): Modifier =
    if (withRipple) {
        this.clickable { onClick() }
    } else {
        this.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            onClick()
        }
    }
