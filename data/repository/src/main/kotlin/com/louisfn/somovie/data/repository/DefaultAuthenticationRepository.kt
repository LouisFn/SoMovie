package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.datasource.AuthenticationRemoteDataSource
import javax.inject.Inject

interface AuthenticationRepository {

    @AnyThread
    suspend fun getRequestToken(): String

    @AnyThread
    suspend fun createNewSession(requestToken: String): String
}

internal class DefaultAuthenticationRepository @Inject constructor(
    private val remoteDataSource: AuthenticationRemoteDataSource,
) : AuthenticationRepository {

    override suspend fun getRequestToken(): String = remoteDataSource.getRequestToken()

    override suspend fun createNewSession(requestToken: String): String =
        remoteDataSource.createNewSession(requestToken)
}
