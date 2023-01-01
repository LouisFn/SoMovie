package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.HttpException
import com.louisfn.somovie.data.network.StatusCode.RESOURCE_REQUESTED_NOT_FOUND
import com.louisfn.somovie.data.network.response.MovieCreditsResponse
import com.louisfn.somovie.domain.exception.MovieNotFoundException
import javax.inject.Inject

interface MovieCreditsRemoteDataSource {

    @AnyThread
    suspend fun getMovieCredits(movieId: Long): MovieCreditsResponse
}

internal class DefaultMovieCreditsRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor
) : MovieCreditsRemoteDataSource {

    override suspend fun getMovieCredits(movieId: Long): MovieCreditsResponse =
        executor.execute(mapHttpException = ::mapHttpException) {
            it.getMovieCredits(movieId)
        }

    @AnyThread
    private fun mapHttpException(e: HttpException) =
        when (e.statusCode) {
            RESOURCE_REQUESTED_NOT_FOUND -> MovieNotFoundException()
            else -> null
        }
}
