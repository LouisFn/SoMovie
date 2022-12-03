package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    @AnyThread
    suspend fun addToWatchlist(movieId: Long)

    @AnyThread
    suspend fun removeFromWatchlist(movieId: Long)

    @AnyThread
    fun watchlistPagingChanges(
        pagingConfig: PagingConfig,
        cacheTimeout: Long = DEFAULT_CACHE_TIMEOUT
    ): Flow<PagingData<Movie>>

    companion object {
        private const val DEFAULT_CACHE_TIMEOUT = 0L
    }
}
