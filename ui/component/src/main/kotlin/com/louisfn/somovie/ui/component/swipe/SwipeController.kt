@file:OptIn(ExperimentalMaterialApi::class)

package com.louisfn.somovie.ui.component.swipe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ThresholdConfig
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.input.pointer.util.addPointerInputChange
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.center
import com.louisfn.somovie.ui.common.extension.toPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin
import kotlin.math.withSign

@Stable
class SwipeController(
    thresholdConfig: ThresholdConfig,
    private val velocityThreshold: Float,
    private val size: IntSize,
    private val maxRotationInDegrees: Float,
    private val swipeAnimationSpec: AnimationSpec<Offset>,
    private val cancelAnimationSpec: AnimationSpec<Offset>,
    private val density: Density,
    private val scope: CoroutineScope,
    private val onDragging: (SwipeDirection, ratio: Float) -> Unit,
    private val onSwiped: (SwipeDirection) -> Unit,
    private val onDisappeared: (SwipeDirection) -> Unit,
    private val onCanceled: () -> Unit
) {

    private val velocityTracker = VelocityTracker()
    private val threshold =
        with(thresholdConfig) { density.computeThreshold(0f, size.width.toFloat()) }
    private val maxItemWidthWhenRotate =
        size.height * sin(maxRotationInDegrees).absoluteValue +
            size.width * cos(maxRotationInDegrees).absoluteValue

    private val offsetAnimatable = Animatable(Offset.Zero, Offset.VectorConverter)
    val offset by derivedStateOf { offsetAnimatable.value }
    val rotation by derivedStateOf { offsetAnimatable.value.x / size.center.x * maxRotationInDegrees }

    var isGestureEnabled by mutableStateOf(true)
        private set

    fun onDrag(change: PointerInputChange, dragAmount: Offset) {
        velocityTracker.addPointerInputChange(change)

        scope.launch {
            offsetAnimatable.snapTo(offset.plus(dragAmount))
            onDragging(
                SwipeDirection.fromOffset(offset.x),
                offset.x.absoluteValue / size.width
            )
        }
    }

    fun onDragEnd() {
        val isThresholdReached = isOffsetThresholdReached() || isVelocityThresholdReached()
        velocityTracker.resetTracking()

        scope.launch {
            if (isThresholdReached) {
                isGestureEnabled = false

                val targetOffsetX = maxItemWidthWhenRotate.withSign(offset.x)
                val direction = SwipeDirection.fromOffset(targetOffsetX)

                onSwiped(direction)
                offsetAnimatable.animateTo(
                    targetValue = offset.copy(x = targetOffsetX),
                    animationSpec = swipeAnimationSpec
                )
                onDisappeared(direction)
            } else {
                onCanceled()
                offsetAnimatable.animateTo(Offset.Zero, cancelAnimationSpec)
            }
        }
    }

    private fun isOffsetThresholdReached(): Boolean = offset.x.absoluteValue > threshold

    private fun isVelocityThresholdReached(): Boolean = velocityTracker.calculateVelocity()
        .let { velocity ->
            velocity.x.absoluteValue > velocityThreshold && offset.x.sign == velocity.x.sign
        }
}

@Composable
internal fun rememberSwipeController(
    size: IntSize,
    maxRotationInDegrees: Float,
    thresholdConfig: ThresholdConfig,
    velocityThreshold: Dp,
    swipeAnimationSpec: AnimationSpec<Offset>,
    cancelAnimationSpec: AnimationSpec<Offset>,
    onDragging: (SwipeDirection, ratio: Float) -> Unit,
    onSwiped: (SwipeDirection) -> Unit,
    onDisappeared: (SwipeDirection) -> Unit,
    onCanceled: () -> Unit
): SwipeController {
    val currentOnDragging by rememberUpdatedState(onDragging)
    val currentOnSwipe by rememberUpdatedState(onSwiped)

    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val velocityThresholdInPx = velocityThreshold.toPx()

    return remember(
        size,
        maxRotationInDegrees,
        thresholdConfig,
        swipeAnimationSpec,
        cancelAnimationSpec
    ) {
        SwipeController(
            size = size,
            maxRotationInDegrees = maxRotationInDegrees,
            thresholdConfig = thresholdConfig,
            velocityThreshold = velocityThresholdInPx,
            swipeAnimationSpec = swipeAnimationSpec,
            cancelAnimationSpec = cancelAnimationSpec,
            density = density,
            scope = scope,
            onDragging = currentOnDragging,
            onSwiped = currentOnSwipe,
            onDisappeared = onDisappeared,
            onCanceled = onCanceled
        )
    }
}
