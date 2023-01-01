package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.body.AddToWatchlistBody
import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.data.network.response.PaginatedResultsResponse
import javax.inject.Inject

interface WatchlistRemoteDataSource {

    @AnyThread
    suspend fun addToWatchlist(accountId: Long, addToWatchlistBody: AddToWatchlistBody)

    @AnyThread
    suspend fun getWatchList(accountId: Long, page: Int): PaginatedResultsResponse<MovieResponse>
}

internal class DefaultWatchlistRemoteDataSource @Inject constructor(
    private val apiServiceExecutor: ApiServiceExecutor
) : WatchlistRemoteDataSource {

    override suspend fun addToWatchlist(accountId: Long, addToWatchlistBody: AddToWatchlistBody) {
        apiServiceExecutor.execute {
            it.addToWatchlist(accountId, addToWatchlistBody)
        }
    }

    override suspend fun getWatchList(accountId: Long, page: Int): PaginatedResultsResponse<MovieResponse> =
        apiServiceExecutor.execute {
            it.getMovieWatchlist(accountId, page)
        }
}
