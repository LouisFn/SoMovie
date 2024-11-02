package com.louisfn.somovie.feature.moviedetails.poster

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
internal fun Modifier.draggablePoster(
    stateController: PosterStateController,
): Modifier {
    val currentStateController by rememberUpdatedState(stateController)

    return pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change, dragAmount ->
                change.consume()
                currentStateController.onDrag(dragAmount)
            },
            onDragEnd = {
                currentStateController.onDragEnd()
            },
        )
    }
}
