package com.louisfn.somovie.domain.usecase.watchlist

import com.louisfn.somovie.domain.repository.WatchlistRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import javax.inject.Inject

class RemoveFromWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : SuspendUseCase<Long, Unit>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(movieId: Long) {
        watchlistRepository.removeFromWatchlist(movieId)
    }
}
