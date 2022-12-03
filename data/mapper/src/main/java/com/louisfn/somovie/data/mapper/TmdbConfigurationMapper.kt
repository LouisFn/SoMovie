package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.common.provider.DateTimeProvider
import com.louisfn.somovie.data.datastore.model.TmdbConfigurationData
import com.louisfn.somovie.data.network.response.ConfigurationResponse
import com.louisfn.somovie.domain.model.TmdbConfiguration
import javax.inject.Inject

class TmdbConfigurationMapper @Inject constructor(
    private val dateTimeProvider: DateTimeProvider
) {

    //region Map datastore to domain

    fun mapToDomain(tmdbConfigurationData: TmdbConfigurationData) = TmdbConfiguration(
        images = tmdbConfigurationData.images?.let { mapToDomain(it) },
        changesKeys = tmdbConfigurationData.changesKeys,
        updatedAt = tmdbConfigurationData.updatedAt
    )

    private fun mapToDomain(images: TmdbConfigurationData.Images) = TmdbConfiguration.Images(
        baseUrl = images.baseUrl,
        secureBaseUrl = images.secureBaseUrl,
        backdropSizes = images.backdropSizes,
        logoSizes = images.logoSizes,
        posterSizes = images.posterSizes,
        profileSizes = images.profileSizes,
        stillSizes = images.stillSizes
    )

    //endregion

    //region Map domain to datastore

    fun mapToData(domain: TmdbConfiguration) = TmdbConfigurationData(
        images = domain.images?.let { mapToData(it) },
        changesKeys = domain.changesKeys,
        updatedAt = domain.updatedAt
    )

    private fun mapToData(domain: TmdbConfiguration.Images) = TmdbConfigurationData.Images(
        baseUrl = domain.baseUrl,
        secureBaseUrl = domain.secureBaseUrl,
        backdropSizes = domain.backdropSizes,
        logoSizes = domain.logoSizes,
        posterSizes = domain.posterSizes,
        profileSizes = domain.profileSizes,
        stillSizes = domain.stillSizes
    )

    //endregion

    //region Map response to datastore

    fun mapToData(response: ConfigurationResponse) =
        TmdbConfigurationData(
            images = mapToData(response.images),
            changesKeys = response.changesKeys,
            updatedAt = dateTimeProvider.now()
        )

    private fun mapToData(response: ConfigurationResponse.Images) =
        TmdbConfigurationData.Images(
            baseUrl = response.baseUrl,
            secureBaseUrl = response.secureBaseUrl,
            backdropSizes = response.backdropSizes,
            logoSizes = response.logoSizes,
            posterSizes = response.posterSizes,
            profileSizes = response.profileSizes,
            stillSizes = response.stillSizes
        )

    //endregion
}
