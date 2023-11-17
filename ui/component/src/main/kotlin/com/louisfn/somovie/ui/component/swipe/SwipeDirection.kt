package com.louisfn.somovie.ui.component.swipe

enum class SwipeDirection {
    LEFT,
    RIGHT,
    ;

    companion object {
        fun fromOffset(offsetX: Float) = if (offsetX > 0) RIGHT else LEFT
    }
}
