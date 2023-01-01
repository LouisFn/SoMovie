package com.louisfn.somovie.feature.home.discover

import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.ui.common.model.ImmutableList
import javax.inject.Inject

internal class DiscoverMovieItemMapper @Inject constructor() {

    fun map(movies: List<Movie>): ImmutableList<DiscoverUiState.MovieItem> =
        ImmutableList(movies.mapNotNull(::map))

    fun map(movie: Movie): DiscoverUiState.MovieItem? =
        movie.posterPath?.let { posterPath ->
            DiscoverUiState.MovieItem(
                id = movie.id,
                title = movie.title,
                posterPath = posterPath
            )
        }
}
