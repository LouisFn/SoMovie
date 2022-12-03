package com.louisfn.somovie.data.repository

import com.louisfn.somovie.data.network.datasource.AuthenticationRemoteDataSource
import com.louisfn.somovie.domain.repository.AuthenticationRepository
import javax.inject.Inject

internal class DefaultAuthenticationRepository @Inject constructor(
    private val remoteDataSource: AuthenticationRemoteDataSource
) : AuthenticationRepository {

    override suspend fun getRequestToken(): String = remoteDataSource.getRequestToken()

    override suspend fun createNewSession(requestToken: String): String =
        remoteDataSource.createNewSession(requestToken)
}
