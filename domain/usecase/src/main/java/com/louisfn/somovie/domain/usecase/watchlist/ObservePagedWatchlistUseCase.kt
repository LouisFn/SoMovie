package com.louisfn.somovie.domain.usecase.watchlist

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.repository.WatchlistRepository
import com.louisfn.somovie.domain.usecase.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePagedWatchlistUseCase @Inject constructor(
    private val watchlistRepository: WatchlistRepository
) : PagingUseCase<Unit, Movie>() {

    override fun execute(parameter: Unit): Flow<PagingData<Movie>> =
        watchlistRepository.watchlistPagingChanges(PAGING_CONFIG)

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = true
        )
    }
}
