package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.HttpException
import com.louisfn.somovie.data.network.StatusCode.RESOURCE_REQUESTED_NOT_FOUND
import com.louisfn.somovie.data.network.response.MovieVideoResponse
import com.louisfn.somovie.domain.exception.MovieNotFoundException
import javax.inject.Inject

interface MovieVideoRemoteDataSource {

    @AnyThread
    suspend fun getMovieVideos(movieId: Long): List<MovieVideoResponse>
}

internal class DefaultMovieVideoRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor
) : MovieVideoRemoteDataSource {

    override suspend fun getMovieVideos(movieId: Long): List<MovieVideoResponse> =
        executor.execute(mapHttpException = ::mapHttpException) {
            it.getMovieVideos(movieId).results
        }

    @AnyThread
    private fun mapHttpException(e: HttpException) =
        when (e.statusCode) {
            RESOURCE_REQUESTED_NOT_FOUND -> MovieNotFoundException()
            else -> null
        }
}
