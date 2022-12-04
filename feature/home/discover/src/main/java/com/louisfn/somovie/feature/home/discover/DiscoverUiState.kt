package com.louisfn.somovie.feature.home.discover

import com.louisfn.somovie.domain.model.PosterPath
import com.louisfn.somovie.ui.common.model.ImmutableList

internal sealed interface DiscoverUiState {

    object None : DiscoverUiState

    object Loading : DiscoverUiState

    object Retry : DiscoverUiState

    data class Discover(
        val items: ImmutableList<MovieItem>,
        val logInSnackbarState: LogInSnackbarState
    ) : DiscoverUiState {
        enum class LogInSnackbarState {
            VISIBLE,
            SHAKING,
            HIDDEN,
        }
    }

    data class MovieItem(
        val id: Long,
        val title: String,
        val posterPath: PosterPath
    )
}
