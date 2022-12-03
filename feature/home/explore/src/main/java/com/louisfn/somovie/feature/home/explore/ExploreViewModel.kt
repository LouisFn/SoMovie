package com.louisfn.somovie.feature.home.explore

import androidx.annotation.AnyThread
import androidx.lifecycle.viewModelScope
import com.louisfn.somovie.common.Result
import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.common.asFlowResult
import com.louisfn.somovie.common.extension.safeCollect
import com.louisfn.somovie.common.isError
import com.louisfn.somovie.common.isLoading
import com.louisfn.somovie.common.onResultError
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.usecase.movie.ObserveExploreMoviesUseCase
import com.louisfn.somovie.domain.usecase.movie.RefreshExploreMoviesUseCase
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.base.NoneAction
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import com.louisfn.somovie.ui.common.model.ImmutableList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ExploreViewModel @Inject constructor(
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    observeExploreMoviesUseCase: ObserveExploreMoviesUseCase,
    private val refreshExploreMoviesUseCase: RefreshExploreMoviesUseCase,
    private val errorsDispatcher: ErrorsDispatcher
) : BaseViewModel<NoneAction>(defaultDispatcher) {

    private val refreshResultState = MutableStateFlow<Result<Unit>?>(null)

    val uiState: StateFlow<ExploreUiState> =
        combine(
            observeExploreMoviesUseCase(Unit)
                .map { ImmutableList(it.map { pair -> pair.first to ImmutableList(pair.second) }) },
            refreshResultState
        ) { movies, refreshResult ->
            createUiState(movies, refreshResult)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = ExploreUiState.None
            )

    init {
        refresh()
    }

    @AnyThread
    private fun createUiState(
        moviesByCategory: ImmutableList<Pair<ExploreCategory, ImmutableList<Movie>>>,
        refreshResult: Result<Unit>?
    ): ExploreUiState = when {
        moviesByCategory.none { it.second.isEmpty() } ->
            ExploreUiState.Explore(moviesByCategory)
        refreshResult.isLoading ->
            ExploreUiState.Loading
        refreshResult.isError ->
            ExploreUiState.Retry
        else ->
            ExploreUiState.None
    }

    @AnyThread
    fun refresh() {
        viewModelScope.launch(defaultDispatcher) {
            asFlowResult { refreshExploreMoviesUseCase(Unit) }
                .onResultError(errorsDispatcher::dispatch)
                .safeCollect(
                    onEach = refreshResultState::emit,
                    onError = errorsDispatcher::dispatch
                )
        }
    }
}
