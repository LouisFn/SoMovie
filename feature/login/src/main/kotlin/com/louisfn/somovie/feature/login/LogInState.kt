package com.louisfn.somovie.feature.login

import android.net.Uri

sealed interface LogInState {

    object Idle : LogInState

    object Loading : LogInState

    @JvmInline
    value class WebView(val uri: Uri) : LogInState

    object Succeed : LogInState

    @JvmInline
    value class Failed(val exception: Exception) : LogInState
}
