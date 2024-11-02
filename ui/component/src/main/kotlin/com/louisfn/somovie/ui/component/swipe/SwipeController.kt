package com.louisfn.somovie.ui.component.swipe

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.VectorConverter
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
    fractionalThreshold: Float,
    private val velocityThreshold: Float,
    private val size: IntSize,
    private val maxRotationInDegrees: Float,
    private val swipeAnimationSpec: AnimationSpec<Offset>,
    private val cancelAnimationSpec: AnimationSpec<Offset>,
    private val scope: CoroutineScope,
    private val onDragging: (SwipeDirection, ratio: Float) -> Unit,
    private val onSwipe: (SwipeDirection) -> Unit,
    private val onDisappear: (SwipeDirection) -> Unit,
    private val onCancel: () -> Unit,
) {

    private val velocityTracker = VelocityTracker()
    private val threshold = size.width.toFloat() * fractionalThreshold
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
                offset.x.absoluteValue / size.width,
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

                onSwipe(direction)
                offsetAnimatable.animateTo(
                    targetValue = offset.copy(x = targetOffsetX),
                    animationSpec = swipeAnimationSpec,
                )
                onDisappear(direction)
            } else {
                onCancel()
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
    fractionalThreshold: Float,
    velocityThreshold: Dp,
    swipeAnimationSpec: AnimationSpec<Offset>,
    cancelAnimationSpec: AnimationSpec<Offset>,
    onDragging: (SwipeDirection, ratio: Float) -> Unit,
    onSwipe: (SwipeDirection) -> Unit,
    onDisappear: (SwipeDirection) -> Unit,
    onCancel: () -> Unit,
): SwipeController {
    val currentOnDragging by rememberUpdatedState(onDragging)
    val currentOnSwipe by rememberUpdatedState(onSwipe)

    val scope = rememberCoroutineScope()
    val velocityThresholdInPx = velocityThreshold.toPx()

    return remember(
        size,
        maxRotationInDegrees,
        fractionalThreshold,
        swipeAnimationSpec,
        cancelAnimationSpec,
    ) {
        SwipeController(
            fractionalThreshold = fractionalThreshold,
            size = size,
            maxRotationInDegrees = maxRotationInDegrees,
            velocityThreshold = velocityThresholdInPx,
            swipeAnimationSpec = swipeAnimationSpec,
            cancelAnimationSpec = cancelAnimationSpec,
            scope = scope,
            onDragging = currentOnDragging,
            onSwipe = currentOnSwipe,
            onDisappear = onDisappear,
            onCancel = onCancel,
        )
    }
}
