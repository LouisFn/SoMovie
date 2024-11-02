package com.louisfn.somovie.ui.component.swipe

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
internal fun Modifier.swipeableItem(
    swipeController: SwipeController,
): Modifier {
    if (!swipeController.isGestureEnabled) return this

    val currentSwipeController by rememberUpdatedState(swipeController)
    return pointerInput(Unit) {
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
