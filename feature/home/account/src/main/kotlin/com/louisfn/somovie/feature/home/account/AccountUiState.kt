package com.louisfn.somovie.feature.home.account

internal sealed interface AccountUiState {

    object None : AccountUiState

    object Disconnected : AccountUiState

    @JvmInline
    value class LoggedIn(val username: String) : AccountUiState
}
