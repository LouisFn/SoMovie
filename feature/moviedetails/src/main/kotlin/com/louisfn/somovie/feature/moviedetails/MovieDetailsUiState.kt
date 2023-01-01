package com.louisfn.somovie.feature.moviedetails

import androidx.annotation.FloatRange
import androidx.compose.runtime.Immutable
import com.louisfn.somovie.domain.model.*
import com.louisfn.somovie.ui.common.model.ImmutableList
import java.time.Duration

@Immutable
internal data class MovieDetailsUiState(
    val headerUiState: HeaderUiState? = null,
    val contentUiState: ContentUiState? = null,
    val watchlistFabState: WatchlistFabState? = null
)

@Immutable
internal data class HeaderUiState(
    val title: String,
    val posterPath: PosterPath?,
    val backdropPaths: ImmutableList<BackdropPath>,
    val tagline: String?,
    @FloatRange(from = 0.0, to = 10.0)
    val voteAverage: Float,
    val voteCount: Int?,
    val tmdbUrl: String,
    val releaseDate: String?
) {
    val hasVotes: Boolean = voteCount != null && voteCount > 0
}

@Immutable
internal sealed interface ContentUiState {
    object Loading : ContentUiState
    object Retry : ContentUiState
    data class Content(
        val originalTitle: String,
        val originalLanguage: String,
        val overview: String,
        val runtime: Duration?,
        val popularity: Float,
        val budget: String,
        val revenue: String,
        val genres: ImmutableList<MovieGenre>,
        val crew: ImmutableList<CrewMember>?,
        val cast: ImmutableList<Actor>?,
        val videos: ImmutableList<YoutubeVideo>?
    ) : ContentUiState
}

internal data class WatchlistFabState(
    val isLoading: Boolean,
    val state: WatchlistState
) {
    enum class WatchlistState { ADD_TO_WATCHLIST, REMOVE_FROM_WATCHLIST }
}
