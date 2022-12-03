package com.louisfn.somovie.app.ui.main

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface MainUiState {
    object Loading : MainUiState
    object Content : MainUiState
}
