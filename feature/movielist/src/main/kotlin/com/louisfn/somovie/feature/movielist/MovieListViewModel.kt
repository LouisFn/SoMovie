package com.louisfn.somovie.feature.movielist

import androidx.annotation.AnyThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.cachedIn
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.core.common.extension.takeAs
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.usecase.movie.MovieInteractor
import com.louisfn.somovie.feature.movielist.MovieListUiState.LoadNextPageState
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.base.NoneAction
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class MovieListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val movieInteractor: MovieInteractor,
    private val errorsDispatcher: ErrorsDispatcher,
) : BaseViewModel<NoneAction>(defaultDispatcher) {

    private val category: ExploreCategory =
        ExploreCategory.valueOf(
            savedStateHandle[MovieListNavigation.ARGS_CATEGORY]
                ?: throw IllegalArgumentException("${MovieListNavigation.ARGS_CATEGORY} not found"),
        )

    private val loadStates = MutableStateFlow<CombinedLoadStates?>(null)

    val pagedMovies = movieInteractor.moviesPagingChanges(category)
        .cachedIn(viewModelScope)

    val uiState: StateFlow<MovieListUiState> =
        loadStates
            .filterNotNull()
            .map(::createUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = MovieListUiState(category),
            )

    private fun createUiState(loadStates: CombinedLoadStates): MovieListUiState {
        val appendLoadState = loadStates.append

        return MovieListUiState(
            category = category,
            loadNextPageState = when (appendLoadState) {
                is LoadState.Error -> LoadNextPageState.FAILED
                is LoadState.Loading -> LoadNextPageState.LOADING
                is LoadState.NotLoading -> LoadNextPageState.IDLE
            },
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
