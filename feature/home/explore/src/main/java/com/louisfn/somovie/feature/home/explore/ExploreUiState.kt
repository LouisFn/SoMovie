package com.louisfn.somovie.feature.home.explore

import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.ui.common.model.ImmutableList

internal sealed interface ExploreUiState {

    object None : ExploreUiState

    object Loading : ExploreUiState

    object Retry : ExploreUiState

    @JvmInline
    value class Explore(val movies: ImmutableList<Pair<ExploreCategory, ImmutableList<Movie>>>) : ExploreUiState
}
