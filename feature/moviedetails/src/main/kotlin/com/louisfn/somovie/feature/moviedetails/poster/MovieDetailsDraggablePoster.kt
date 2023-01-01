package com.louisfn.somovie.feature.moviedetails.poster

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput

internal fun Modifier.draggablePoster(
    stateController: PosterStateController
) = composed {
    val currentStateController by rememberUpdatedState(stateController)

    pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change, dragAmount ->
                change.consume()
                currentStateController.onDrag(dragAmount)
            },
            onDragEnd = {
                currentStateController.onDragEnd()
            }
        )
    }
}
