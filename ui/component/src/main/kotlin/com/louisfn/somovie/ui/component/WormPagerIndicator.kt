package com.louisfn.somovie.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun WormPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    size: Dp = 8.dp,
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    color: Color = activeColor.copy(ContentAlpha.disabled),
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(pagerState.pageCount) {
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(
                            color = color,
                            shape = CircleShape,
                        ),
                )
            }
        }

        Box(
            Modifier
                .wormTransition(
                    pagerState,
                    spacing,
                    activeColor,
                )
                .size(size),
        )
    }
}

private fun Modifier.wormTransition(
    pagerState: PagerState,
    spacing: Dp,
    color: Color = Color.White,
) = drawBehind {
    val distance = size.width + spacing.roundToPx()
    val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
    val wormOffset = scrollPosition % 1 * 2

    val xPos = scrollPosition.toInt() * distance
    val head = xPos + distance * 0f.coerceAtLeast(wormOffset - 1)
    val tail = xPos + size.width + 1f.coerceAtMost(wormOffset) * distance

    val worm = RoundRect(
        left = head,
        top = 0f,
        right = tail,
        bottom = size.height,
        cornerRadius = CornerRadius(50f),
    )

    val path = Path().apply { addRoundRect(worm) }
    drawPath(path = path, color = color)
}
