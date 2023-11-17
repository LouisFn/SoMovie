package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import androidx.annotation.FloatRange
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.parameter.MovieDiscoverSortParameter
import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.data.network.response.PaginatedResultsResponse
import com.louisfn.somovie.domain.model.MovieDiscoverSortBy
import com.louisfn.somovie.domain.model.MovieDiscoverSortByDirection
import javax.inject.Inject

interface MovieDiscoverRemoteDataSource {

    @AnyThread
    suspend fun getMovieDiscover(
        sortBy: MovieDiscoverSortBy,
        sortByDirection: MovieDiscoverSortByDirection,
        minVoteCount: Int,
        @FloatRange(from = 0.0, to = 10.0) minVoteAverage: Float,
        page: Int,
    ): PaginatedResultsResponse<MovieResponse>
}

internal class DefaultMovieDiscoverRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor,
) : MovieDiscoverRemoteDataSource {

    override suspend fun getMovieDiscover(
        sortBy: MovieDiscoverSortBy,
        sortByDirection: MovieDiscoverSortByDirection,
        minVoteCount: Int,
        @FloatRange(from = 0.0, to = 10.0) minVoteAverage: Float,
        page: Int,
    ): PaginatedResultsResponse<MovieResponse> =
        executor.execute {
            it.getMovieDiscover(
                sortBy = MovieDiscoverSortParameter(sortBy, sortByDirection),
                minVoteCount = minVoteCount,
                minVoteAverage = minVoteAverage,
                page = page,
            )
        }
}
