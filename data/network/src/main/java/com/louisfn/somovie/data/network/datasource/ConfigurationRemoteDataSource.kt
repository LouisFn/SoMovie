package com.louisfn.somovie.data.network.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.network.ApiServiceExecutor
import com.louisfn.somovie.data.network.response.ConfigurationResponse
import javax.inject.Inject

interface ConfigurationRemoteDataSource {

    @AnyThread
    suspend fun getConfiguration(): ConfigurationResponse
}

internal class DefaultConfigurationRemoteDataSource @Inject constructor(
    private val executor: ApiServiceExecutor
) : ConfigurationRemoteDataSource {

    override suspend fun getConfiguration(): ConfigurationResponse =
        executor.execute {
            it.getConfiguration()
        }
}
