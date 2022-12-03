package com.louisfn.somovie.feature.home.discover

import androidx.annotation.AnyThread
import androidx.lifecycle.viewModelScope
import com.louisfn.somovie.common.Result
import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.common.asFlowResult
import com.louisfn.somovie.common.data
import com.louisfn.somovie.common.extension.safeCollect
import com.louisfn.somovie.common.isError
import com.louisfn.somovie.common.isLoading
import com.louisfn.somovie.common.onResultError
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.usecase.discover.GetMovieDiscoverUseCase
import com.louisfn.somovie.domain.usecase.watchlist.AddToWatchlistUseCase
import com.louisfn.somovie.feature.home.discover.DiscoverUiState.MovieItem
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.base.NoneAction
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import com.louisfn.somovie.ui.common.model.ImmutableList
import com.louisfn.somovie.ui.component.swipe.SwipeDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DiscoverViewModel @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val getMovieDiscoverUseCase: GetMovieDiscoverUseCase,
    private val addToWatchlistUseCase: AddToWatchlistUseCase,
    private val movieItemMapper: DiscoverMovieItemMapper,
    private val errorsDispatcher: ErrorsDispatcher
) : BaseViewModel<NoneAction>(defaultDispatcher) {

    private val moviesState = MutableStateFlow(emptyList<Movie>())
    private val fetchNewMoviesResultState = MutableStateFlow<Result<List<Movie>>?>(null)

    val uiState: StateFlow<DiscoverUiState> =
        combine(
            moviesState
                .map(movieItemMapper::map),
            fetchNewMoviesResultState
        ) { items, fetchNewMoviesResultState ->
            createDiscoverUiState(items, fetchNewMoviesResultState)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = DiscoverUiState.None
            )

    init {
        fetchNewDiscoverMovies()
    }

    @AnyThread
    private fun createDiscoverUiState(
        items: ImmutableList<MovieItem>,
        fetchMovieResultState: Result<List<Movie>>?
    ): DiscoverUiState = when {
        items.isNotEmpty() -> DiscoverUiState.Discover(items)
        fetchMovieResultState.isLoading -> DiscoverUiState.Loading
        fetchMovieResultState.isError -> DiscoverUiState.Retry
        else -> DiscoverUiState.None
    }

    @AnyThread
    fun onMovieSwiped(movieItem: MovieItem, direction: SwipeDirection) {
        viewModelScope.launch(defaultDispatcher) {
            if (direction.shouldAddMovieToWatchlist()) {
                try {
                    addToWatchlistUseCase(movieItem.id)
                } catch (e: Exception) {
                    errorsDispatcher.dispatch(e)
                }
            }
        }
    }

    @AnyThread
    fun onMovieDisappeared(movieItem: MovieItem) {
        viewModelScope.launch(defaultDispatcher) {
            moviesState.update { movies -> movies.filter { it.id != movieItem.id } }

            if (moviesState.value.size <= MIN_MOVIE_COUNT_BEFORE_FETCH) {
                fetchNewDiscoverMovies()
            }
        }
    }

    @AnyThread
    fun fetchNewDiscoverMovies() {
        viewModelScope.launch(defaultDispatcher) {
            val currentState = fetchNewMoviesResultState.getAndUpdate { Result.Loading() }
            if (currentState !is Result.Loading) {
                asFlowResult { getMovieDiscoverUseCase(Unit) }
                    .onResultError(errorsDispatcher::dispatch)
                    .safeCollect(
                        onEach = { result ->
                            moviesState.update { it + result.data.orEmpty() }
                            fetchNewMoviesResultState.emit(result)
                        },
                        onError = errorsDispatcher::dispatch
                    )
            }
        }
    }

    @AnyThread
    private fun SwipeDirection.shouldAddMovieToWatchlist() = this == SwipeDirection.RIGHT

    companion object {
        private const val MIN_MOVIE_COUNT_BEFORE_FETCH = 5
    }
}
