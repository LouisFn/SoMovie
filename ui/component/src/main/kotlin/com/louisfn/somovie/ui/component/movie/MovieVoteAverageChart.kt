package com.louisfn.somovie.ui.component.movie

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.louisfn.somovie.ui.theme.AppColor

@Composable
fun MovieVoteAverageChart(
    @FloatRange(from = 0.0, to = 10.0) average: Float,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.colors.surface,
    strokeWidth: Dp = 3.dp,
    textStyle: TextStyle = MaterialTheme.typography.caption,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(background),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxSize(),
            progress = average / 10,
            color = MaterialTheme.colors.secondary,
            strokeWidth = strokeWidth,
        )
        Text(
            text = String.format("%.1f", average),
            style = textStyle,
            color = AppColor.Silver,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
@Preview
private fun MovieVoteAverageChartPreview() {
    MovieVoteAverageChart(average = 8.4f)
}
