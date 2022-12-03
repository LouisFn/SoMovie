package com.louisfn.somovie.test.testfixtures.android.repository

import com.louisfn.somovie.domain.model.TmdbConfiguration
import com.louisfn.somovie.domain.repository.TmdbConfigurationRepository
import com.louisfn.somovie.test.testfixtures.android.data.domain.FakeTmdbConfigurationFactory
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
