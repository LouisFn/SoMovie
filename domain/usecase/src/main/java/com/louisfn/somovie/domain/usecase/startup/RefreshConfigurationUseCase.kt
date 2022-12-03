package com.louisfn.somovie.domain.usecase.startup

import com.louisfn.somovie.common.annotation.ApplicationScope
import com.louisfn.somovie.domain.repository.TmdbConfigurationRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RefreshConfigurationUseCase @Inject constructor(
    private val configurationRepository: TmdbConfigurationRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) : SuspendUseCase<Unit, Unit>() {

    override suspend fun execute(parameter: Unit) {
        with(configurationRepository) {
            if (getTmdbConfiguration().isFetched) {
                applicationScope.launch { refreshIfCacheExpired() }
            } else {
                refreshIfCacheExpired()
            }
        }
    }
}
