package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.response.AccountResponse
import javax.inject.Inject

interface AccountRemoteDataSource {

    @AnyThread
    suspend fun getAccountResponse(): AccountResponse
}

internal class DefaultAccountRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor
) : AccountRemoteDataSource {

    override suspend fun getAccountResponse(): AccountResponse =
        executor.execute {
            it.getAccount()
        }
}
