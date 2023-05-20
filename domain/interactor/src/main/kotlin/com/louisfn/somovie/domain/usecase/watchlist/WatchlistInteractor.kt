package com.louisfn.somovie.domain.usecase.watchlist

import androidx.annotation.AnyThread
import androidx.paging.PagingConfig
import com.louisfn.somovie.data.repository.WatchlistRepository
import javax.inject.Inject

class WatchlistInteractor @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) {

    @AnyThread
    fun watchlistPagingChanges() = watchlistRepository.watchlistPagingChanges(PAGING_CONFIG)

    @AnyThread
    suspend fun addToWatchlist(movieId: Long) = watchlistRepository.addToWatchlist(movieId)

    @AnyThread
    suspend fun removeFromWatchlist(movieId: Long) =
        watchlistRepository.removeFromWatchlist(movieId)

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = true
        )
    }
}
