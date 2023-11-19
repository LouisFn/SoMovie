package com.louisfn.somovie.feature.login

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun LogInWebView(
    uri: Uri,
    logInManager: LogInManager,
    modifier: Modifier = Modifier,
) {
    LogInWebView(
        uri = uri,
        modifier = modifier,
        onApproved = { logInManager.onApproved() },
        onDenied = { logInManager.onDenied() },
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LogInWebView(
    uri: Uri,
    modifier: Modifier = Modifier,
    onApproved: () -> Unit = {},
    onDenied: () -> Unit = {},
) {
    val state = rememberWebViewState(uri.toString())

    val currentOnApproved by rememberUpdatedState(onApproved)
    val currentOnDenied by rememberUpdatedState(onDenied)

    val currentUrl = state.lastLoadedUrl
    LaunchedEffect(key1 = currentUrl) {
        currentUrl?.run {
            when {
                contains(LogInConfig.APPROVE_PATH) -> currentOnApproved()
                contains(LogInConfig.DENY_PATH) -> currentOnDenied()
                else -> {}
            }
        }
    }

    BackHandler(true) { currentOnDenied() }

    WebView(
        modifier = modifier,
        state = state,
        onCreated = {
            it.settings.javaScriptEnabled = true
        },
    )
}
