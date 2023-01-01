package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.database.entity.GenreEntity
import com.louisfn.somovie.domain.model.MovieGenre
import javax.inject.Inject

class GenreMapper @Inject constructor() {

    //region Map entity to domain

    fun mapToDomain(entities: List<GenreEntity>): List<MovieGenre> = entities.map(::mapToDomain)

    fun mapToDomain(entity: GenreEntity) = MovieGenre(
        id = entity.id,
        name = entity.name
    )

    //endregion
}
