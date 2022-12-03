package com.louisfn.somovie.data.repository

import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.mapper.AccountMapper
import com.louisfn.somovie.data.network.datasource.AccountRemoteDataSource
import com.louisfn.somovie.domain.model.Account
import com.louisfn.somovie.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke
import javax.inject.Inject

internal class DefaultAccountRepository @Inject constructor(
    private val remoteDataSource: AccountRemoteDataSource,
    private val mapper: AccountMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : AccountRepository {

    override suspend fun getAccount(): Account = defaultDispatcher {
        mapper.mapToDomain(remoteDataSource.getAccountResponse())
    }
}
