package com.louisfn.somovie.data.repository

import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.common.extension.isExpired
import com.louisfn.somovie.common.provider.DateTimeProvider
import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.TmdbConfigurationData
import com.louisfn.somovie.data.mapper.TmdbConfigurationMapper
import com.louisfn.somovie.data.network.datasource.ConfigurationRemoteDataSource
import com.louisfn.somovie.domain.model.TmdbConfiguration
import com.louisfn.somovie.domain.repository.TmdbConfigurationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
import javax.inject.Inject

internal class DefaultTmdbConfigurationRepository @Inject constructor(
    private val remoteDataSource: ConfigurationRemoteDataSource,
    private val localDataSource: DataStoreLocalDataSource<TmdbConfigurationData>,
    private val mapper: TmdbConfigurationMapper,
    private val dateTimeProvider: DateTimeProvider,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : TmdbConfigurationRepository {

    override fun tmdbConfigurationChanges(): Flow<TmdbConfiguration> =
        localDataSource.dataChanges()
            .map(mapper::mapToDomain)
            .flowOn(defaultDispatcher)

    override suspend fun getTmdbConfiguration(): TmdbConfiguration = defaultDispatcher {
        mapper.mapToDomain(localDataSource.getData())
    }

    override suspend fun updateTmdbConfiguration(transform: suspend (t: TmdbConfiguration) -> TmdbConfiguration) {
        localDataSource.updateData {
            mapper.mapToData(transform(mapper.mapToDomain(it)))
        }
    }

    override suspend fun refreshIfCacheExpired(cacheTimeout: Long) {
        val isExpired = getTmdbConfiguration().updatedAt.isExpired(dateTimeProvider.now(), cacheTimeout)
        if (isExpired) {
            localDataSource.updateData {
                mapper.mapToData(remoteDataSource.getConfiguration())
            }
        }
    }
}
