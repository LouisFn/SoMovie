package com.louisfn.somovie.data.mapper

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.database.entity.MovieImageEntity
import com.louisfn.somovie.data.database.entity.MovieImageEntity.Type
import com.louisfn.somovie.data.network.response.MovieImagesResponse
import com.louisfn.somovie.domain.model.BackdropPath
import com.louisfn.somovie.domain.model.MovieImages
import com.louisfn.somovie.domain.model.PosterPath
import javax.inject.Inject

class MovieImageMapper @Inject constructor() {

    //region Map entity to domain

    @AnyThread
    fun mapToDomain(entities: List<MovieImageEntity>) = MovieImages(
        backdrops = entities.filter { it.type == Type.BACKDROP }
            .map { mapToBackdropPathDomain(it) },
        posters = entities.filter { it.type == Type.POSTER }
            .map { mapToPosterPathDomain(it) },
    )

    @AnyThread
    private fun mapToBackdropPathDomain(entity: MovieImageEntity) = MovieImages.Image(
        path = BackdropPath(entity.path),
        width = entity.width,
    )

    @AnyThread
    private fun mapToPosterPathDomain(entity: MovieImageEntity) = MovieImages.Image(
        path = PosterPath(entity.path),
        width = entity.width,
    )

    //endregion

    //region Map response to entity

    @AnyThread
    fun mapResponseToEntity(response: MovieImagesResponse): List<MovieImageEntity> =
        response.backdrops.map { mapResponseToEntity(response.movieId, Type.BACKDROP, it) } +
            response.posters.map { mapResponseToEntity(response.movieId, Type.POSTER, it) }

    @AnyThread
    private fun mapResponseToEntity(
        movieId: Long,
        type: Type,
        response: MovieImagesResponse.Image,
    ) = MovieImageEntity(
        movieId = movieId,
        path = response.filePath,
        width = response.width,
        type = type,
    )
}
