package com.louisfn.somovie.feature.moviedetails.poster

import androidx.annotation.AnyThread
import androidx.annotation.UiThread
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.louisfn.somovie.ui.common.extension.roundToPx
import com.louisfn.somovie.ui.common.extension.screenHeightWithInset
import com.louisfn.somovie.ui.common.extension.screenWidth
import com.louisfn.somovie.ui.theme.Dimens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val MinDragDistanceToReduce = 96.dp

@Stable
internal class PosterStateController(
    reducedCoordinates: LayoutCoordinates,
    private val scope: CoroutineScope,
    private val minDragDistanceToReduce: Int,
    private val screenSize: Size,
    private var onStateChanged: (MovieDetailsPosterState) -> Unit,
) {
    private var currentState: MovieDetailsPosterState = MovieDetailsPosterState.REDUCED

    private val reducedSize = reducedCoordinates.size.toSize()
    private val reducedOffset = reducedCoordinates.positionInParent()
    private val expandedSize = Size(width = screenSize.width, height = screenSize.width / Dimens.PosterRatio)
    private val expandedOffset = Offset(x = 0f, y = screenSize.height / 2 - expandedSize.height / 2)

    private var dragOffset = Offset.Zero

    private val sizeAnimatable = Animatable(reducedSize, Size.VectorConverter)
    private val offsetAnimatable = Animatable(reducedOffset, Offset.VectorConverter)

    val size by derivedStateOf { sizeAnimatable.value }
    val offset by derivedStateOf { offsetAnimatable.value }

    @UiThread
    fun animateToState(state: MovieDetailsPosterState) {
        currentState = state
        when (state) {
            MovieDetailsPosterState.EXPANDED -> animateToExpandedState()
            MovieDetailsPosterState.REDUCED -> animateToReducedState()
        }
    }

    @UiThread
    fun onDrag(dragAmount: Offset) {
        if (currentState != MovieDetailsPosterState.EXPANDED) return

        dragOffset = dragOffset.plus(dragAmount)

        val newWidth =
            if (dragOffset.getDistance() <= minDragDistanceToReduce) {
                screenSize.width - dragOffset.getDistance()
            } else {
                sizeAnimatable.value.width
            }

        val x = (screenSize.width - newWidth) / 2
        val sizeOffset = Offset(x = x, y = x / Dimens.PosterRatio)

        val newOffset = expandedOffset
            .plus(dragOffset)
            .plus(sizeOffset)

        scope.launch { offsetAnimatable.snapTo(newOffset) }
        scope.launch { sizeAnimatable.snapTo(Size(width = newWidth, height = newWidth / Dimens.PosterRatio)) }
    }

    @UiThread
    fun onDragEnd() {
        if (currentState != MovieDetailsPosterState.EXPANDED) return

        if (dragOffset.getDistance() > minDragDistanceToReduce) {
            onStateChanged(MovieDetailsPosterState.REDUCED)
        } else {
            animateToExpandedState()
        }

        dragOffset = Offset.Zero
    }

    @AnyThread
    private fun animateToExpandedState() {
        scope.launch { offsetAnimatable.animateTo(expandedOffset) }
        scope.launch { sizeAnimatable.animateTo(expandedSize) }
    }

    @AnyThread
    private fun animateToReducedState() {
        scope.launch { offsetAnimatable.animateTo(reducedOffset) }
        scope.launch { sizeAnimatable.animateTo(reducedSize) }
    }
}

@Composable
internal fun rememberPosterStateController(
    reducedCoordinates: LayoutCoordinates,
    onStateChanged: (MovieDetailsPosterState) -> Unit,
): PosterStateController {
    val scope = rememberCoroutineScope()
    val minDragDistanceToReduce = MinDragDistanceToReduce.roundToPx()
    val screenSize = with(LocalConfiguration.current) { Size(width = screenWidth, height = screenHeightWithInset) }

    return remember(reducedCoordinates) {
        PosterStateController(
            minDragDistanceToReduce = minDragDistanceToReduce,
            reducedCoordinates = reducedCoordinates,
            screenSize = screenSize,
            onStateChanged = onStateChanged,
            scope = scope,
        )
    }
}
