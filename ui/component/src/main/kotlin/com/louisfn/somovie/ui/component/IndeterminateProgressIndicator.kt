package com.louisfn.somovie.ui.component

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag

@Composable
fun IndeterminateProgressIndicator(modifier: Modifier = Modifier) {
    androidx.compose.material.CircularProgressIndicator(
        modifier = modifier
            .semantics { testTag = ComponentTestTag.IndeterminateProgressIndicator },
        color = MaterialTheme.colors.onBackground,
    )
}
