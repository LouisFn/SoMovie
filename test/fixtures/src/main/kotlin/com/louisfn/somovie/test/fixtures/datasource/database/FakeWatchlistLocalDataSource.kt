package com.louisfn.somovie.test.fixtures.datasource.database

import androidx.paging.PagingSource
import com.louisfn.somovie.data.database.datasource.WatchlistLocalDataSource
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.test.shared.paging.FakePagingSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.TestScope

class FakeWatchlistLocalDataSource(
    private val scope: TestScope,
) : WatchlistLocalDataSource {

    private val _watchlist = MutableStateFlow<List<MovieEntity>>(emptyList())
    val watchlist: StateFlow<List<MovieEntity>> = _watchlist

    private val _moviesWatchListState = MutableStateFlow<List<MovieWatchlistState>>(emptyList())
    val moviesWatchListState: StateFlow<List<MovieWatchlistState>> = _moviesWatchListState

    override fun getPagingWatchlist(): PagingSource<Int, MovieEntity> =
        FakePagingSource(watchlist, scope)

    override suspend fun insertOrIgnoreToWatchlist(movieEntities: List<MovieEntity>) {
        _watchlist.update { it + movieEntities }
    }

    override suspend fun updateWatchlistState(movieId: Long, watchlist: Boolean) {
        _moviesWatchListState.update { states ->
            val index = states.indexOfFirst { it.movieId == movieId }
            val item = states.getOrNull(index)

            if (item == null) {
                states + MovieWatchlistState(movieId, watchlist)
            } else {
                states.toMutableList().apply { set(index, MovieWatchlistState(movieId, watchlist)) }
            }
        }
    }

    override suspend fun deleteFromWatchlist(movieId: Long) {
        _watchlist.update { watchlist -> watchlist.filter { it.id != movieId } }
    }

    override suspend fun deleteWatchlist() {
        _watchlist.update { emptyList() }
    }

    data class MovieWatchlistState(
        val movieId: Long,
        val watchlist: Boolean,
    )
}
