package com.louisfn.somovie.feature.movielist

import androidx.annotation.AnyThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.common.extension.takeAs
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.usecase.movie.ObservePagedMoviesUseCase
import com.louisfn.somovie.feature.movielist.MovieListUiState.LoadNextPageState
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.base.NoneAction
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
internal class MovieListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    observePagedMoviesUseCase: ObservePagedMoviesUseCase,
    private val errorsDispatcher: ErrorsDispatcher
) : BaseViewModel<NoneAction>(defaultDispatcher) {

    private val category: ExploreCategory =
        ExploreCategory.valueOf(
            savedStateHandle[MovieListNavigation.ARGS_CATEGORY]
                ?: throw IllegalArgumentException("${MovieListNavigation.ARGS_CATEGORY} not found")
        )

    private val loadStates = MutableStateFlow<CombinedLoadStates?>(null)

    val pagedMovies = observePagedMoviesUseCase(category)
        .cachedIn(viewModelScope)

    val uiState: StateFlow<MovieListUiState> =
        loadStates
            .filterNotNull()
            .map(::createUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MovieListUiState(category)
            )

    private fun createUiState(loadStates: CombinedLoadStates): MovieListUiState {
        val appendLoadState = loadStates.append

        return MovieListUiState(
            category = category,
            loadNextPageState = when (appendLoadState) {
                is LoadState.Error -> LoadNextPageState.FAILED
                is LoadState.Loading -> LoadNextPageState.LOADING
                is LoadState.NotLoading -> LoadNextPageState.IDLE
            }
        )
    }

    //region Paging

    @AnyThread
    fun onLoadStateChanged(loadStates: CombinedLoadStates) {
        this.loadStates.value = loadStates

        loadStates.refresh.takeAs<LoadState.Error>()?.let {
            onPagingError(it.error)
        }
        loadStates.append.takeAs<LoadState.Error>()?.let {
            onPagingError(it.error)
        }
    }

    @AnyThread
    fun onPagingError(e: Throwable) {
        errorsDispatcher.dispatch(e)
    }

    //endregion
}
