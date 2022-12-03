package com.louisfn.somovie.feature.home.discover

import com.louisfn.somovie.domain.model.PosterPath
import com.louisfn.somovie.ui.common.model.ImmutableList

internal sealed interface DiscoverUiState {

    object None : DiscoverUiState

    object Loading : DiscoverUiState

    object Retry : DiscoverUiState

    @JvmInline
    value class Discover(val items: ImmutableList<MovieItem>) : DiscoverUiState

    data class MovieItem(
        val id: Long,
        val title: String,
        val posterPath: PosterPath
    )
}
