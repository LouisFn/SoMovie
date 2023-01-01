package com.louisfn.somovie.test.fixtures.data.repository

import com.louisfn.somovie.data.repository.TmdbConfigurationRepository
import com.louisfn.somovie.domain.model.TmdbConfiguration
import com.louisfn.somovie.test.fixtures.domain.FakeTmdbConfigurationFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeTmdbConfigurationRepository : TmdbConfigurationRepository {

    private val tmdbConfigurationState = MutableStateFlow(FakeTmdbConfigurationFactory.default)

    override fun tmdbConfigurationChanges(): Flow<TmdbConfiguration> = tmdbConfigurationState

    override suspend fun getTmdbConfiguration(): TmdbConfiguration = tmdbConfigurationState.value

    override suspend fun updateTmdbConfiguration(transform: suspend (t: TmdbConfiguration) -> TmdbConfiguration) {
        tmdbConfigurationState.update { transform(it) }
    }

    override suspend fun refreshIfCacheExpired(cacheTimeout: Long) {
        //
    }

    fun setTmdbConfiguration(tmdbConfiguration: TmdbConfiguration) {
        tmdbConfigurationState.value = tmdbConfiguration
    }
}
