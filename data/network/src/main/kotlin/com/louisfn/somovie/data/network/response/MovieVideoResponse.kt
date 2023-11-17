package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class MovieVideoResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "iso_639_1")
    val iso6391: String,
    @Json(name = "iso_3166_1")
    val iso31661: String,
    @Json(name = "key")
    val key: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "official")
    val official: Boolean,
    @Json(name = "published_at")
    val publishedAt: OffsetDateTime,
    @Json(name = "site")
    val site: Site,
    @Json(name = "size")
    val size: Int,
    @Json(name = "type")
    val type: Type,
) {

    @JsonClass(generateAdapter = false)
    enum class Site(val json: String) {
        YOUTUBE("YouTube"),
        UNKNOWN(""),
    }

    @JsonClass(generateAdapter = false)
    enum class Type(val json: String) {
        TRAILERS("Trailer"),
        TEASERS("Teaser"),
        CLIPS("Clip"),
        BEHIND("Behind the Scenes"),
        BLOOPERS("Bloopers"),
        FEATURETTES("Featurette"),
    }
}
