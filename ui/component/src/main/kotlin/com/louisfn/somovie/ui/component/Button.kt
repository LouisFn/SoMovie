package com.louisfn.somovie.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DefaultTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.TextButtonContentPadding,
) {
    androidx.compose.material.TextButton(
        modifier = modifier,
        contentPadding = contentPadding,
        onClick = onClick,
    ) {
        Text(
            color = MaterialTheme.colors.secondary,
            text = text,
        )
    }
}
