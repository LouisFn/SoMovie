package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.database.entity.CompanyEntity
import com.louisfn.somovie.data.network.response.MovieDetailsResponse.ProductionCompanyResponse
import com.louisfn.somovie.domain.model.Company
import com.louisfn.somovie.domain.model.LogoPath
import javax.inject.Inject

class CompanyMapper @Inject constructor() {

    //region Map entity to domain

    fun mapToDomain(entities: List<CompanyEntity>): List<Company> = entities.map(::mapToDomain)

    fun mapToDomain(entity: CompanyEntity) = Company(
        id = entity.id,
        name = entity.name,
        logoPath = entity.logoPath?.let(::LogoPath),
    )

    //endregion

    //region Map response to entity

    fun mapToEntity(entities: List<ProductionCompanyResponse>): List<CompanyEntity> = entities.map(::mapToEntity)

    fun mapToEntity(response: ProductionCompanyResponse) =
        CompanyEntity(
            id = response.id,
            logoPath = response.logoPath,
            name = response.name,
        )

    //endregion
}
