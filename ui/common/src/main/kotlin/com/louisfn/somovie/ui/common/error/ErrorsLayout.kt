package com.louisfn.somovie.ui.common.error

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.louisfn.somovie.ui.common.model.ImmutableList

private const val SimpleMessageErrorBackgroundAlpha = 0.6f

@Composable
fun ErrorsLayout(
    errors: ImmutableList<Error>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        errors.forEach {
            when (it) {
                is SimpleMessageError -> SimpleMessageError(it)
            }
        }
    }
}

@Composable
private fun SimpleMessageError(error: SimpleMessageError) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.error.copy(SimpleMessageErrorBackgroundAlpha))
            .padding(8.dp),
    ) {
        Text(
            modifier = Modifier,
            text = error.message,
            color = MaterialTheme.colors.onError,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center,
        )
    }
}
