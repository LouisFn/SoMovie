package com.louisfn.somovie.feature.home.watchlist

import com.louisfn.somovie.domain.model.Movie

internal sealed interface WatchlistUiState {

    object None : WatchlistUiState

    data class AccountLoggedIn(
        val contentState: ContentState = ContentState.NONE,
        val loadNextPageState: LoadNextPageState = LoadNextPageState.IDLE
    ) : WatchlistUiState {
        enum class ContentState { NONE, LOADING, RETRY, SUCCESS }
        enum class LoadNextPageState { IDLE, LOADING, RETRY }
    }

    object AccountDisconnected : WatchlistUiState
}

internal data class MovieItem(
    val movie: Movie,
    val isHidden: Boolean
)
