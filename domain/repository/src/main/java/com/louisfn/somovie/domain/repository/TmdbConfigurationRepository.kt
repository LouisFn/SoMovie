package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.model.TmdbConfiguration
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

interface TmdbConfigurationRepository {

    @AnyThread
    fun tmdbConfigurationChanges(): Flow<TmdbConfiguration>

    @AnyThread
    suspend fun getTmdbConfiguration(): TmdbConfiguration

    @AnyThread
    suspend fun updateTmdbConfiguration(transform: suspend (t: TmdbConfiguration) -> TmdbConfiguration)

    @AnyThread
    suspend fun refreshIfCacheExpired(cacheTimeout: Long = DEFAULT_CACHE_TIMEOUT)

    companion object {
        private val DEFAULT_CACHE_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }
}
