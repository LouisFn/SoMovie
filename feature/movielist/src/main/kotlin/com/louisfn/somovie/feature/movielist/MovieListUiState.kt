package com.louisfn.somovie.feature.movielist

import com.louisfn.somovie.domain.model.ExploreCategory

internal data class MovieListUiState(
    val category: ExploreCategory,
    val loadNextPageState: LoadNextPageState = LoadNextPageState.IDLE,
) {
    enum class LoadNextPageState { IDLE, LOADING, FAILED }
}
