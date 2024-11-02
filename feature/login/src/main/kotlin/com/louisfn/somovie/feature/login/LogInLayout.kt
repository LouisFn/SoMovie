package com.louisfn.somovie.feature.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.component.IndeterminateProgressIndicator

@Composable
fun LogInLayout(
    logInManager: LogInManager,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(button: @Composable (modifier: Modifier) -> Unit) -> Unit =
        { it(Modifier.align(Alignment.Center)) },
) {
    val uiState by logInManager.state.collectAsStateLifecycleAware()

    LaunchedEffect(Unit) {
        logInManager.reset()
    }

    LogInLayout(
        uiState = uiState,
        modifier = modifier
            .semantics { testTag = LogInTestTag.LogInLayout },
        onLogInButtonClick = logInManager::start,
        onLogInApprove = logInManager::onApprove,
        onLogInDeny = logInManager::onDeny,
        buttonDecorator = content,
    )
}

@Composable
private fun LogInLayout(
    uiState: LogInState,
    modifier: Modifier = Modifier,
    onLogInButtonClick: () -> Unit = {},
    onLogInApprove: () -> Unit = {},
    onLogInDeny: () -> Unit = {},
    buttonDecorator: @Composable BoxScope.(button: @Composable (modifier: Modifier) -> Unit) -> Unit,
) {
    Box(modifier = modifier) {
        when (uiState) {
            is LogInState.Loading ->
                IndeterminateProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            is LogInState.Idle ->
                buttonDecorator { internalModifier ->
                    LogInButton(
                        modifier = internalModifier,
                        onClick = onLogInButtonClick,
                    )
                }
            is LogInState.WebView ->
                LogInWebView(
                    uri = uiState.uri,
                    modifier = Modifier.fillMaxSize(),
                    onApprove = onLogInApprove,
                    onDeny = onLogInDeny,
                )
            else -> Unit
        }
    }
}
