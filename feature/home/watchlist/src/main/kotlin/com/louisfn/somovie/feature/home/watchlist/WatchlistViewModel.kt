package com.louisfn.somovie.feature.home.watchlist

import androidx.annotation.AnyThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.louisfn.somovie.core.common.annotation.ApplicationScope
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.core.common.extension.takeAs
import com.louisfn.somovie.core.logger.Logger
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.usecase.authentication.ObserveIsLoggedInUseCase
import com.louisfn.somovie.domain.usecase.watchlist.ObservePagedWatchlistUseCase
import com.louisfn.somovie.domain.usecase.watchlist.RemoveFromWatchlistUseCase
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.ContentState
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.LoadNextPageState
import com.louisfn.somovie.ui.common.base.BaseViewModel
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import com.louisfn.somovie.ui.common.extension.isError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Inject

@HiltViewModel
internal class WatchlistViewModel @Inject constructor(
    observePagedWatchlistUseCase: ObservePagedWatchlistUseCase,
    @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    private val removeFromWatchlistUseCase: RemoveFromWatchlistUseCase,
    private val observeIsLoggedInUseCase: ObserveIsLoggedInUseCase,
    private val errorsDispatcher: ErrorsDispatcher,
    @ApplicationScope private val applicationScope: CoroutineScope
) : BaseViewModel<WatchlistAction>(defaultDispatcher) {

    private val pagingState = MutableStateFlow<PagingState?>(null)
    private val hiddenMovieItemIds = MutableStateFlow(emptyList<Long>())
    private val pendingSwipedMovieItemIds = CopyOnWriteArrayList<Long>()

    //region UiState

    private val accountLoggedInUiState: Flow<WatchlistUiState.AccountLoggedIn> =
        pagingState
            .map(::createUiState)

    private val accountDisconnectedUiState: Flow<WatchlistUiState.AccountDisconnected> =
        flowOf(WatchlistUiState.AccountDisconnected)

    val uiState: StateFlow<WatchlistUiState> =
        observeIsLoggedInUseCase(Unit)
            .flatMapLatest {
                if (it) accountLoggedInUiState
                else accountDisconnectedUiState
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = WatchlistUiState.None
            )

    //endregion

    //region Paged movies

    private val pagedMovies =
        observePagedWatchlistUseCase(Unit)
            .cachedIn(viewModelScope)

    val pagedMovieItems =
        observeIsLoggedInUseCase(Unit)
            .filter { it }
            .flatMapLatest {
                combine(
                    pagedMovies,
                    hiddenMovieItemIds
                ) { paged, hiddenItemIds ->
                    paged.map { movie ->
                        MovieItem(movie = movie, isHidden = movie.id in hiddenItemIds)
                    }
                }
                    .flowOn(defaultDispatcher)
            }

    //endregion

    @AnyThread
    fun onViewHidden() {
        removeAllPendingSwipedMovieFromWatchlist()
    }

    @AnyThread
    private fun createUiState(pagingState: PagingState?): WatchlistUiState.AccountLoggedIn {
        if (pagingState == null) {
            return WatchlistUiState.AccountLoggedIn()
        }

        val refreshLoadState = pagingState.loadStates.refresh
        val appendLoadState = pagingState.loadStates.append

        return WatchlistUiState.AccountLoggedIn(
            contentState = when {
                pagingState.itemCount == 0 && refreshLoadState.isError -> ContentState.RETRY
                pagingState.itemCount == 0 -> ContentState.LOADING
                else -> ContentState.SUCCESS
            },
            loadNextPageState = when (appendLoadState) {
                is LoadState.Error -> LoadNextPageState.RETRY
                is LoadState.Loading -> LoadNextPageState.LOADING
                is LoadState.NotLoading -> LoadNextPageState.IDLE
            }
        )
    }

    //region Paging state

    @AnyThread
    fun onLoadStateChanged(loadStates: CombinedLoadStates, itemCount: Int) {
        pagingState.value = PagingState(loadStates, itemCount)

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

    //region Remove from watchlist

    @AnyThread
    fun onSwipeToDismiss(movie: Movie) {
        viewModelScope.launch(defaultDispatcher) {
            addAsHidden(movie.id)
            addAsPendingSwiped(movie.id)
            emitAction(WatchlistAction.ShowUndoSwipeToDismissSnackbar(movie.id))
        }
    }

    @AnyThread
    fun onUndoSwipeToDismissSnackbarDismissed(movieId: Long) {
        viewModelScope.launch(defaultDispatcher) {
            removeAsPendingSwiped(movieId)
            removeFromWatchlist(movieId)
        }
    }

    @AnyThread
    fun onUndoSwipeToDismissSnackbarActionPerformed(movieId: Long) {
        viewModelScope.launch(defaultDispatcher) {
            removeAsHidden(movieId)
            removeAsPendingSwiped(movieId)
        }
    }

    @AnyThread
    private fun removeAllPendingSwipedMovieFromWatchlist() {
        applicationScope.launch(defaultDispatcher) {
            pendingSwipedMovieItemIds
                .forEach {
                    launch {
                        try {
                            removeFromWatchlistWithTimeout(it)
                        } catch (e: Exception) {
                            Logger.e(e)
                        }
                    }
                }

            pendingSwipedMovieItemIds.clear()
        }
    }

    @WorkerThread
    private suspend fun removeFromWatchlist(movieId: Long) {
        try {
            withContext(NonCancellable) {
                removeFromWatchlistWithTimeout(movieId)
            }
        } catch (e: Exception) {
            removeAsHidden(movieId)
            errorsDispatcher.dispatch(e)
        }
    }

    @AnyThread
    private suspend fun removeFromWatchlistWithTimeout(movieId: Long) {
        withTimeout(REMOVE_FROM_WATCHLIST_TIMEOUT) {
            removeFromWatchlistUseCase(movieId)
        }
    }

    @WorkerThread
    private fun addAsHidden(movieId: Long) {
        hiddenMovieItemIds.update { it + movieId }
    }

    @WorkerThread
    private fun removeAsHidden(movieId: Long) {
        hiddenMovieItemIds.update { ids -> ids.filter { it != movieId } }
    }

    @WorkerThread
    private fun addAsPendingSwiped(movieId: Long) {
        pendingSwipedMovieItemIds.add(movieId)
    }

    @WorkerThread
    private fun removeAsPendingSwiped(movieId: Long) {
        pendingSwipedMovieItemIds.removeIf { it == movieId }
    }

    //endregion

    companion object {
        private const val REMOVE_FROM_WATCHLIST_TIMEOUT = 10_000L
    }

    data class PagingState(
        val loadStates: CombinedLoadStates,
        val itemCount: Int
    )
}
