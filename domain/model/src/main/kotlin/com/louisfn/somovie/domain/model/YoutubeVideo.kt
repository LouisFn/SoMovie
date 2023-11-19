package com.louisfn.somovie.domain.model

import java.time.OffsetDateTime

data class YoutubeVideo(
    val id: String,
    val key: String,
    val name: String,
    val type: Type,
    val official: Boolean,
    val publishedAt: OffsetDateTime,
) {

    enum class Type(val order: Int) {
        TRAILERS(0),
        TEASERS(1),
        CLIPS(2),
        BEHIND(3),
        BLOOPERS(4),
        FEATURETTES(5),
    }

    val thumbnailUrl =
        "https://img.youtube.com/vi/$key/mqdefault.jpg"
}
