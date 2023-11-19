package com.louisfn.somovie.feature.moviedetails

import androidx.annotation.AnyThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.louisfn.somovie.core.common.Result
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.core.common.asFlowResult
import com.louisfn.somovie.core.common.extension.combine
import com.louisfn.somovie.core.common.extension.safeCollect
import com.louisfn.somovie.core.common.onResultError
import com.louisfn.somovie.domain.model.BackdropPath
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.model.MovieCredits
import com.louisfn.somovie.domain.model.YoutubeVideo
import com.louisfn.somovie.domain.usecase.movie.MovieCreditsInteractor
import com.louisfn.somovie.domain.usecase.movie.MovieImageInteractor
import com.louisfn.somovie.domain.usecase.movie.MovieInteractor
import com.louisfn.somovie.domain.usecase.video.MovieVideosInteractor
import com.louisfn.somovie.domain.usecase.watchlist.WatchlistInteractor
import com.louisfn.somovie.feature.moviedetails.WatchlistFabState.WatchlistState
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import com.louisfn.somovie.ui.common.extension.toDollarFormat
import com.louisfn.somovie.ui.common.extension.toReleaseFormat
import com.louisfn.somovie.ui.common.model.ImmutableList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class MovieDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieInteractor: MovieInteractor,
    private val movieImageInteractor: MovieImageInteractor,
    private val movieCreditsInteractor: MovieCreditsInteractor,
    private val movieVideosInteractor: MovieVideosInteractor,
    private val watchlistInteractor: WatchlistInteractor,
    private val errorsDispatcher: ErrorsDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val movieId: Long =
        checkNotNull(savedStateHandle[MovieDetailsNavigation.ARGS_MOVIE_ID]) {
            "SavedStateHandle key ${MovieDetailsNavigation.ARGS_MOVIE_ID} not found"
        }

    private val refreshMovieResultState = MutableStateFlow<Result<Unit>?>(null)
    private val updateWatchlistResultState = MutableStateFlow<Result<Unit>?>(null)

    val uiState: StateFlow<MovieDetailsUiState> =
        combine(
            movieChanges(),
            movieBackdropsChanges(),
            movieCreditsChanges(),
            movieYoutubeVideoChanges(),
            refreshMovieResultState,
            updateWatchlistResultState,
        ) { movie, backdrops, credits, videos, refreshMovieResult, updateWatchlistResult ->
            MovieDetailsUiState(
                headerUiState = createHeaderUiState(movie, backdrops),
                contentUiState = createContentUiState(movie, credits, videos, refreshMovieResult),
                watchlistFabState = createWatchlistUiState(movie, updateWatchlistResult),
            )
        }
            .flowOn(defaultDispatcher)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MovieDetailsUiState(),
            )

    init {
        refreshMovieDetails()
    }

    @AnyThread
    private fun createHeaderUiState(
        movie: Movie,
        backdrops: List<BackdropPath>,
    ) = HeaderUiState(
        title = movie.title,
        posterPath = movie.posterPath,
        backdropPaths = (
            backdrops.takeIf { it.isNotEmpty() }
                ?: listOfNotNull(movie.backdropPath)
            ).let(::ImmutableList),
        tagline = movie.details?.tagline,
        voteAverage = movie.voteAverage,
        voteCount = movie.details?.voteCount,
        tmdbUrl = movie.tmdbUrl,
        releaseDate = movie.releaseDate?.toReleaseFormat(),
    )

    @AnyThread
    private fun createContentUiState(
        movie: Movie,
        credits: MovieCredits,
        videos: List<YoutubeVideo>,
        refreshMovieResult: Result<Unit>?,
    ): ContentUiState {
        val details = movie.details

        if (details != null) {
            return ContentUiState.Content(
                runtime = details.runtime,
                popularity = details.popularity,
                budget = details.budget.toDollarFormat(),
                revenue = details.revenue.toDollarFormat(),
                genres = details.genres.let(::ImmutableList),
                crew = credits.crewMembers.takeIf { it.isNotEmpty() }?.let(::ImmutableList),
                cast = credits.actors.takeIf { it.isNotEmpty() }?.let(::ImmutableList),
                videos = videos.takeIf { it.isNotEmpty() }?.let(::ImmutableList),
                overview = movie.overview,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
            )
        }

        return if (refreshMovieResult is Result.Error) {
            ContentUiState.Retry
        } else {
            ContentUiState.Loading
        }
    }

    @AnyThread
    private fun createWatchlistUiState(
        movie: Movie,
        updateWatchlistResultState: Result<Unit>?,
    ): WatchlistFabState? =
        movie.watchlist?.let { watchlist ->
            WatchlistFabState(
                isLoading = updateWatchlistResultState is Result.Loading,
                state = if (watchlist) WatchlistState.REMOVE_FROM_WATCHLIST else WatchlistState.ADD_TO_WATCHLIST,
            )
        }

    @AnyThread
    fun switchWatchlistState() {
        viewModelScope.launch(defaultDispatcher) {
            val flow = when (uiState.value.watchlistFabState?.state) {
                WatchlistState.ADD_TO_WATCHLIST ->
                    asFlowResult { watchlistInteractor.addToWatchlist(movieId) }
                WatchlistState.REMOVE_FROM_WATCHLIST ->
                    asFlowResult { watchlistInteractor.removeFromWatchlist(movieId) }
                else -> null
            }

            flow
                ?.onResultError(errorsDispatcher::dispatch)
                ?.safeCollect(
                    onEach = updateWatchlistResultState::emit,
                    onError = errorsDispatcher::dispatch,
                )
        }
    }

    @AnyThread
    fun retry() {
        refreshMovieDetails()
    }

    @AnyThread
    private fun refreshMovieDetails() {
        viewModelScope.launch(defaultDispatcher) {
            asFlowResult { movieInteractor.refreshMovie(movieId) }
                .onResultError(errorsDispatcher::dispatch)
                .safeCollect(
                    onEach = refreshMovieResultState::emit,
                    onError = errorsDispatcher::dispatch,
                )
        }
    }

    @AnyThread
    private fun movieChanges(): Flow<Movie> =
        movieInteractor.movieChanges(movieId)
            .catch { handleError(it) }

    @AnyThread
    private fun movieBackdropsChanges(): Flow<List<BackdropPath>> =
        movieImageInteractor.movieImagesChanges(movieId)
            .catch { handleError(it) }
            .map { images -> images.backdrops.map { it.path } }

    @AnyThread
    private fun movieCreditsChanges(): Flow<MovieCredits> =
        movieCreditsInteractor.movieCreditsChanges(movieId)
            .catch { handleError(it) }

    @AnyThread
    private fun movieYoutubeVideoChanges(): Flow<List<YoutubeVideo>> =
        movieVideosInteractor.movieVideosYoutubeChanges(movieId)
            .catch { handleError(it) }

    @AnyThread
    private fun handleError(e: Throwable) {
        errorsDispatcher.dispatch(e)
    }
}
