package com.louisfn.somovie.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.louisfn.somovie.ui.common.R as commonR

@Composable
fun Retry(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .semantics { testTag = ComponentTestTag.Retry },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DefaultImage(
            modifier = Modifier.width(128.dp),
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
            painter = painterResource(commonR.drawable.ic_cloud_alert),
        )
        Text(
            text = stringResource(id = commonR.string.common_retry_description),
            style = MaterialTheme.typography.subtitle1,
        )
        ColumnSpacer(8.dp)
        RetryButton(onClick = onClick)
    }
}

@Composable
fun RetryButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Text(text = stringResource(id = commonR.string.common_retry_button))
    }
}

@Composable
fun TextRetryButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    DefaultTextButton(
        text = stringResource(id = commonR.string.common_retry_button),
        modifier = modifier
            .semantics { testTag = ComponentTestTag.TextRetry },
        onClick = onClick,
    )
}

@Preview
@Composable
private fun Retry() {
    Retry(onClick = {})
}
