package com.louisfn.somovie.test.fixtures.datasource.network

import com.louisfn.somovie.data.network.body.AddToWatchlistBody
import com.louisfn.somovie.data.network.datasource.WatchlistRemoteDataSource
import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.data.network.response.PaginatedResultsResponse
import com.louisfn.somovie.test.shared.FakeWebServer
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Suppress("MemberVisibilityCanBePrivate")
class FakeWatchlistRemoteDataSource(
    var movies: List<MovieResponse>,
    var accountId: Long,
    private val fakeWebServer: FakeWebServer,
    private val nbrOfResultsByPage: Int = DEFAULT_NBR_OF_RESULTS_BY_PAGE,
) : WatchlistRemoteDataSource {

    private val paginatedWatchlist = mutableListOf<MutableList<MovieResponse>>()
    private val mutex = Mutex()

    override suspend fun getWatchList(
        accountId: Long,
        page: Int,
    ): PaginatedResultsResponse<MovieResponse> =
        mutex.withLock {
            fakeWebServer.execute()

            require(this.accountId == accountId)

            PaginatedResultsResponse(
                totalResults = paginatedWatchlist.sumOf { it.size },
                totalPages = paginatedWatchlist.size,
                page = page,
                results = paginatedWatchlist[page - 1],
            )
        }

    override suspend fun addToWatchlist(accountId: Long, addToWatchlistBody: AddToWatchlistBody) {
        mutex.withLock {
            fakeWebServer.execute()

            require(this.accountId == accountId)

            val movie = movies.first { it.id == addToWatchlistBody.movieId }
            if (addToWatchlistBody.watchlist) {
                addToWatchlist(movie)
            } else {
                removeFromWatchlist(movie)
            }
        }
    }

    fun addToWatchlist(movies: List<MovieResponse>) {
        movies.forEach(::addToWatchlist)
    }

    fun addToWatchlist(movie: MovieResponse) {
        when {
            paginatedWatchlist.size == 0 ->
                paginatedWatchlist.add(mutableListOf(movie))
            paginatedWatchlist.last().size >= nbrOfResultsByPage ->
                paginatedWatchlist.add(mutableListOf(movie))
            else ->
                paginatedWatchlist.last().add(movie)
        }
    }

    fun removeFromWatchlist(movie: MovieResponse) {
        paginatedWatchlist.forEach { it.remove(movie) }
    }

    fun getFullWatchlist() = paginatedWatchlist.flatten()

    companion object {
        private const val DEFAULT_NBR_OF_RESULTS_BY_PAGE = 5
    }
}
