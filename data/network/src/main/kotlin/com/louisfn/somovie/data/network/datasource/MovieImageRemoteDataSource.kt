package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.HttpException
import com.louisfn.somovie.data.network.StatusCode.RESOURCE_REQUESTED_NOT_FOUND
import com.louisfn.somovie.data.network.response.MovieImagesResponse
import com.louisfn.somovie.domain.exception.MovieNotFoundException
import javax.inject.Inject

interface MovieImageRemoteDataSource {

    @AnyThread
    suspend fun getMovieImages(movieId: Long): MovieImagesResponse
}

internal class DefaultMovieImageRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor
) : MovieImageRemoteDataSource {

    override suspend fun getMovieImages(movieId: Long): MovieImagesResponse =
        executor.execute(mapHttpException = ::mapHttpException) {
            it.getMovieImages(movieId)
        }

    @AnyThread
    private fun mapHttpException(e: HttpException) =
        when (e.statusCode) {
            RESOURCE_REQUESTED_NOT_FOUND -> MovieNotFoundException()
            else -> null
        }
}
