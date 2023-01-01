package com.louisfn.somovie.feature.home.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.louisfn.somovie.feature.login.LogInLayout
import com.louisfn.somovie.feature.login.LogInManager
import com.louisfn.somovie.feature.login.LogInViewModel
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.component.DefaultTopAppBar
import com.louisfn.somovie.ui.common.R as commonR

@Composable
@Suppress("ViewModelForwarding")
internal fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    logInViewModel: LogInViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateLifecycleAware()

    AccountScreen(
        state = state,
        logInManager = logInViewModel,
        onLogOutButtonClick = { viewModel.logOut() }
    )
}

@Composable
private fun AccountScreen(
    state: AccountUiState,
    logInManager: LogInManager,
    onLogOutButtonClick: () -> Unit
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = { DefaultTopAppBar(text = stringResource(id = commonR.string.home_account)) }
    ) {
        AccountContent(
            state = state,
            logInManager = logInManager,
            modifier = Modifier.padding(it),
            onLogOutButtonClick = onLogOutButtonClick
        )
    }
}

@Composable
private fun AccountContent(
    state: AccountUiState,
    logInManager: LogInManager,
    modifier: Modifier = Modifier,
    onLogOutButtonClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.background)
    ) {
        when (state) {
            is AccountUiState.Disconnected -> LogInLayout(
                logInManager = logInManager,
                modifier = Modifier.fillMaxSize()
            )
            is AccountUiState.LoggedIn -> AccountContent(
                username = state.username,
                modifier = Modifier.fillMaxSize(),
                onLogOutButtonClick = onLogOutButtonClick
            )
            else -> Unit
        }
    }
}

@Composable
private fun AccountContent(
    username: String,
    modifier: Modifier = Modifier,
    onLogOutButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(commonR.string.account_logged_in_confirmation, username),
            textAlign = TextAlign.Center
        )
        Button(onClick = onLogOutButtonClick) {
            Text(text = stringResource(id = commonR.string.account_log_out))
        }
    }
}

@Preview
@Composable
private fun AccountScreenPreview() {
    AccountScreen()
}
