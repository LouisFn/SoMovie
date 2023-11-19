package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.HttpException
import com.louisfn.somovie.data.network.StatusCode.RESOURCE_REQUESTED_NOT_FOUND
import com.louisfn.somovie.data.network.response.MovieAccountStateResponse
import com.louisfn.somovie.data.network.response.MovieDetailsResponse
import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.data.network.response.PaginatedResultsResponse
import com.louisfn.somovie.domain.exception.MovieNotFoundException
import com.louisfn.somovie.domain.model.ExploreCategory
import javax.inject.Inject

interface MovieRemoteDataSource {

    @AnyThread
    suspend fun getMovies(
        category: ExploreCategory,
        page: Int,
    ): PaginatedResultsResponse<MovieResponse>

    @AnyThread
    suspend fun getPopular(page: Int): PaginatedResultsResponse<MovieResponse>

    @AnyThread
    suspend fun getNowPlaying(page: Int): PaginatedResultsResponse<MovieResponse>

    @AnyThread
    suspend fun getTopRated(page: Int): PaginatedResultsResponse<MovieResponse>

    @AnyThread
    suspend fun getUpcoming(page: Int): PaginatedResultsResponse<MovieResponse>

    @AnyThread
    suspend fun getMovieDetails(movieId: Long): MovieDetailsResponse

    @AnyThread
    suspend fun getMovieAccountStates(movieId: Long): MovieAccountStateResponse
}

internal class DefaultMovieRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor,
) : MovieRemoteDataSource {

    override suspend fun getMovies(
        category: ExploreCategory,
        page: Int,
    ): PaginatedResultsResponse<MovieResponse> =
        when (category) {
            ExploreCategory.UPCOMING -> getUpcoming(page)
            ExploreCategory.NOW_PLAYING -> getNowPlaying(page)
            ExploreCategory.TOP_RATED -> getTopRated(page)
            ExploreCategory.POPULAR -> getPopular(page)
        }

    override suspend fun getPopular(page: Int): PaginatedResultsResponse<MovieResponse> =
        executor.execute {
            it.getPopularMovies(page)
        }

    override suspend fun getNowPlaying(page: Int): PaginatedResultsResponse<MovieResponse> =
        executor.execute {
            it.getNowPlayingMovies(page)
        }

    override suspend fun getTopRated(page: Int): PaginatedResultsResponse<MovieResponse> =
        executor.execute {
            it.getTopRatedMovies(page)
        }

    override suspend fun getUpcoming(page: Int): PaginatedResultsResponse<MovieResponse> =
        executor.execute {
            it.getUpcomingMovies(page)
        }

    override suspend fun getMovieDetails(movieId: Long): MovieDetailsResponse =
        executor.execute(mapHttpException = ::mapHttpException) {
            it.getMovieDetails(movieId)
        }

    override suspend fun getMovieAccountStates(movieId: Long): MovieAccountStateResponse =
        executor.execute(mapHttpException = ::mapHttpException) {
            it.getMovieAccountStates(movieId)
        }

    @AnyThread
    private fun mapHttpException(e: HttpException) =
        when (e.statusCode) {
            RESOURCE_REQUESTED_NOT_FOUND -> MovieNotFoundException()
            else -> null
        }
}
