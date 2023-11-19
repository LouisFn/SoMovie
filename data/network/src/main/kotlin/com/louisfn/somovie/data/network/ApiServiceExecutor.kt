package com.louisfn.somovie.data.network

import androidx.annotation.AnyThread
import javax.inject.Inject

internal class ApiServiceExecutor @Inject constructor(
    private val apiService: ApiService,
    private val apiExceptionMapper: ApiExceptionMapper,
) {

    @AnyThread
    suspend fun <T> execute(
        mapHttpException: ((HttpException) -> Exception?)? = null,
        request: suspend (ApiService) -> T,
    ): T =
        try {
            request(apiService)
        } catch (e: Exception) {
            throw apiExceptionMapper.map(e).let {
                if (it is HttpException) {
                    mapHttpException?.invoke(it) ?: it
                } else {
                    it
                }
            }
        }
}
