package com.louisfn.somovie.ui.common

import com.louisfn.somovie.core.common.extension.distinctMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoadingManager {

    private val countState = MutableStateFlow(0)

    val isLoading
        get() = countState.value > 0

    fun start() {
        countState.update { it + 1 }
    }

    fun stop() {
        countState.update { it - 1 }
    }

    fun isLoadingChanges() = countState.distinctMap { it > 0 }
}
