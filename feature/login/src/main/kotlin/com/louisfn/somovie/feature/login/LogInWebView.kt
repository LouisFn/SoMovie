package com.louisfn.somovie.feature.login

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.louisfn.somovie.ui.component.WebView
import com.louisfn.somovie.ui.component.rememberWebViewState

@Composable
fun LogInWebView(
    uri: Uri,
    logInManager: LogInManager,
    modifier: Modifier = Modifier,
) {
    LogInWebView(
        uri = uri,
        modifier = modifier,
        onApprove = { logInManager.onApprove() },
        onDeny = { logInManager.onDeny() },
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LogInWebView(
    uri: Uri,
    modifier: Modifier = Modifier,
    onApprove: () -> Unit = {},
    onDeny: () -> Unit = {},
) {
    val state = rememberWebViewState(uri.toString())

    val currentonApprove by rememberUpdatedState(onApprove)
    val currentonDeny by rememberUpdatedState(onDeny)

    val currentUrl = state.lastLoadedUrl
    LaunchedEffect(key1 = currentUrl) {
        currentUrl?.run {
            when {
                contains(LogInConfig.APPROVE_PATH) -> currentonApprove()
                contains(LogInConfig.DENY_PATH) -> currentonDeny()
                else -> {}
            }
        }
    }

    BackHandler(true) { currentonDeny() }

    WebView(
        modifier = modifier,
        state = state,
        onCreate = {
            it.settings.javaScriptEnabled = true
        },
    )
}
