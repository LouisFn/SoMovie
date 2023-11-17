package com.louisfn.somovie.test.fixtures.data.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.data.repository.WatchlistRepository
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.test.shared.FakeWebServer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeWatchlistRepository(
    var movies: List<Movie>,
    val fakeWebServer: FakeWebServer,
) : WatchlistRepository {

    private val watchlist = MutableStateFlow<List<Movie>>(emptyList())

    override suspend fun addToWatchlist(movieId: Long) {
        fakeWebServer.execute()

        val movie = movies.first { it.id == movieId }
        watchlist.update { it + movie }
    }

    override suspend fun removeFromWatchlist(movieId: Long) {
        fakeWebServer.execute()

        watchlist.update { list -> list.filter { it.id != movieId } }
    }

    override fun watchlistPagingChanges(
        pagingConfig: PagingConfig,
        cacheTimeout: Long,
    ): Flow<PagingData<Movie>> =
        watchlist
            .map(PagingData.Companion::from)
}
