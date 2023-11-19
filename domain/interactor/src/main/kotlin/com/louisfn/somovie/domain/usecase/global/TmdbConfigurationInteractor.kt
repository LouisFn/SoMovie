package com.louisfn.somovie.domain.usecase.global

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.annotation.ApplicationScope
import com.louisfn.somovie.data.repository.TmdbConfigurationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TmdbConfigurationInteractor @Inject constructor(
    private val configurationRepository: TmdbConfigurationRepository,
    @ApplicationScope private val applicationScope: CoroutineScope,
) {

    @AnyThread
    suspend fun refresh() {
        with(configurationRepository) {
            if (getTmdbConfiguration().isFetched) {
                applicationScope.launch { refreshIfCacheExpired() }
            } else {
                refreshIfCacheExpired()
            }
        }
    }
}
