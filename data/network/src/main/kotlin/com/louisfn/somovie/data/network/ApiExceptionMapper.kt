package com.louisfn.somovie.data.network

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.core.common.extension.fromJson
import com.louisfn.somovie.core.logger.Logger
import com.louisfn.somovie.data.network.di.MoshiApiService
import com.louisfn.somovie.data.network.response.ErrorResponse
import com.louisfn.somovie.domain.exception.NoNetworkException
import com.louisfn.somovie.domain.exception.UnexpectedException
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import retrofit2.HttpException as RetrofitHttpException

internal class ApiExceptionMapper @Inject constructor(
    @MoshiApiService private val moshi: Moshi,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) {

    @AnyThread
    suspend fun map(error: Exception): Exception =
        when (error) {
            is IOException -> NoNetworkException(error)
            is RetrofitHttpException -> mapHttpException(error)
            else -> UnexpectedException(error)
        }

    @AnyThread
    private suspend fun mapHttpException(error: RetrofitHttpException): HttpException {
        val response = error.errorResponse()
        return HttpException(
            httpCode = error.code(),
            statusCode = response?.statusCode,
            statusMessage = response?.statusMessage,
        )
    }

    @AnyThread
    private suspend fun RetrofitHttpException.errorResponse(): ErrorResponse? = defaultDispatcher {
        response()?.errorBody()?.source()?.let {
            try {
                val json = String(it.readByteArray(), StandardCharsets.UTF_8)
                moshi.fromJson(json)
            } catch (e: JsonDataException) {
                Logger.e(e)
                null
            }
        }
    }
}
