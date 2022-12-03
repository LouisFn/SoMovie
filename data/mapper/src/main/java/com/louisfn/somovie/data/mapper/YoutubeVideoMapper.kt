package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.database.entity.YoutubeVideoEntity
import com.louisfn.somovie.data.network.response.MovieVideoResponse
import com.louisfn.somovie.data.network.response.MovieVideoResponse.Type
import com.louisfn.somovie.domain.model.YoutubeVideo
import javax.inject.Inject

class YoutubeVideoMapper @Inject constructor() {

    //region Map entity to domain

    fun mapToDomain(entities: List<YoutubeVideoEntity>): List<YoutubeVideo> =
        entities.map(::mapToDomain)

    private fun mapToDomain(entity: YoutubeVideoEntity) = YoutubeVideo(
        id = entity.id,
        key = entity.key,
        name = entity.name,
        type = mapToDomain(entity.type),
        publishedAt = entity.publishedAt,
        official = entity.official
    )

    private fun mapToDomain(entity: YoutubeVideoEntity.Type) = when (entity) {
        YoutubeVideoEntity.Type.TRAILERS -> YoutubeVideo.Type.TRAILERS
        YoutubeVideoEntity.Type.TEASERS -> YoutubeVideo.Type.TEASERS
        YoutubeVideoEntity.Type.CLIPS -> YoutubeVideo.Type.CLIPS
        YoutubeVideoEntity.Type.BEHIND -> YoutubeVideo.Type.BEHIND
        YoutubeVideoEntity.Type.BLOOPERS -> YoutubeVideo.Type.BLOOPERS
        YoutubeVideoEntity.Type.FEATURETTES -> YoutubeVideo.Type.FEATURETTES
    }

    //endregion

    //region Map response to entity

    fun mapToEntity(
        movieId: Long,
        entities: List<MovieVideoResponse>
    ): List<YoutubeVideoEntity> =
        entities
            .filter { it.site == MovieVideoResponse.Site.YOUTUBE }
            .map { mapToEntity(movieId, it) }

    private fun mapToEntity(
        movieId: Long,
        response: MovieVideoResponse
    ): YoutubeVideoEntity {
        check(response.site == MovieVideoResponse.Site.YOUTUBE)
        return YoutubeVideoEntity(
            id = response.id,
            key = response.key,
            name = response.name,
            type = mapTypeToEntity(response.type),
            official = response.official,
            publishedAt = response.publishedAt,
            movieId = movieId
        )
    }

    private fun mapTypeToEntity(type: Type): YoutubeVideoEntity.Type =
        when (type) {
            Type.TRAILERS -> YoutubeVideoEntity.Type.TRAILERS
            Type.TEASERS -> YoutubeVideoEntity.Type.TEASERS
            Type.CLIPS -> YoutubeVideoEntity.Type.CLIPS
            Type.BEHIND -> YoutubeVideoEntity.Type.BEHIND
            Type.BLOOPERS -> YoutubeVideoEntity.Type.BLOOPERS
            Type.FEATURETTES -> YoutubeVideoEntity.Type.FEATURETTES
        }

    //endregion
}
