package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.database.entity.MovieProductionCountryEntity
import com.louisfn.somovie.data.network.response.MovieDetailsResponse
import com.louisfn.somovie.domain.model.Country
import javax.inject.Inject

class CountryMapper @Inject constructor() {

    //region Map entity to domain

    fun mapToDomain(entities: List<MovieProductionCountryEntity>): List<Country> = entities.map(::mapToDomain)

    fun mapToDomain(entity: MovieProductionCountryEntity) = Country(
        name = entity.name,
        iso31661 = entity.iso31661,
    )

    //endregion

    //region Map response to entity

    fun mapToEntity(
        movieId: Long,
        responses: List<MovieDetailsResponse.ProductionCountryResponse>,
    ): List<MovieProductionCountryEntity> = responses.map { mapToEntity(movieId, it) }

    fun mapToEntity(
        movieId: Long,
        response: MovieDetailsResponse.ProductionCountryResponse,
    ) = MovieProductionCountryEntity(
        iso31661 = response.iso31661,
        name = response.name,
        movieId = movieId,
    )

    //endregion
}
