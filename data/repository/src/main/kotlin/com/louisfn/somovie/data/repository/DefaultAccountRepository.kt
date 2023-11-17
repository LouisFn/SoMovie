package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.mapper.AccountMapper
import com.louisfn.somovie.data.network.datasource.AccountRemoteDataSource
import com.louisfn.somovie.domain.model.Account
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface AccountRepository {

    @AnyThread
    suspend fun getAccount(): Account
}

internal class DefaultAccountRepository @Inject constructor(
    private val remoteDataSource: AccountRemoteDataSource,
    private val mapper: AccountMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : AccountRepository {

    override suspend fun getAccount(): Account = defaultDispatcher {
        mapper.mapToDomain(remoteDataSource.getAccountResponse())
    }
}
