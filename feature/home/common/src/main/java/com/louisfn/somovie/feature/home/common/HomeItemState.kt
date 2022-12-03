package com.louisfn.somovie.feature.home.common

import androidx.annotation.UiThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class HomeItemState {

    private val homeItemReselectListeners = mutableListOf<OnHomeItemReselectListener>()

    @UiThread
    fun homeItemReselect() {
        homeItemReselectListeners.forEach { it.onHomeItemReselect() }
    }

    @UiThread
    fun addOnHomeItemReselectListener(listener: OnHomeItemReselectListener) {
        homeItemReselectListeners.add(listener)
    }

    @UiThread
    fun removeOnHomeItemReselectListener(listener: OnHomeItemReselectListener) {
        homeItemReselectListeners.remove(listener)
    }

    fun interface OnHomeItemReselectListener {
        fun onHomeItemReselect()
    }
}

@Composable
fun rememberHomeItemState() = remember { HomeItemState() }
