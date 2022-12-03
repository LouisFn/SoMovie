package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.HttpException
import com.louisfn.somovie.data.network.StatusCode.SESSION_DENIED
import com.louisfn.somovie.data.network.body.RequestTokenBody
import com.louisfn.somovie.domain.exception.SessionDeniedException
import javax.inject.Inject

interface AuthenticationRemoteDataSource {

    @AnyThread
    suspend fun getRequestToken(): String

    @AnyThread
    suspend fun createNewSession(requestToken: String): String
}

internal class DefaultAuthenticationRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor
) : AuthenticationRemoteDataSource {

    override suspend fun getRequestToken(): String =
        executor.execute(mapHttpException = ::mapHttpException) {
            it.getRequestToken().requestToken
        }

    override suspend fun createNewSession(requestToken: String): String =
        executor.execute {
            it.createNewSession(RequestTokenBody(requestToken)).sessionId
        }

    @AnyThread
    private fun mapHttpException(e: HttpException) =
        when (e.statusCode) {
            SESSION_DENIED -> SessionDeniedException()
            else -> null
        }
}
