package com.louisfn.somovie.ui.component.swipe

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput

internal fun Modifier.swipeableItem(
    swipeController: SwipeController,
) = composed {
    if (!swipeController.isGestureEnabled) return@composed this

    val currentSwipeController by rememberUpdatedState(swipeController)
    pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change, dragAmount ->
                change.consume()
                currentSwipeController.onDrag(change, dragAmount)
            },
            onDragEnd = {
                currentSwipeController.onDragEnd()
            },
        )
    }
}
