package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCreditsResponse(
    @Json(name = "id")
    val movieId: Long,
    @Json(name = "cast")
    val cast: List<Actor>,
    @Json(name = "crew")
    val crew: List<CrewMember>
) {

    @JsonClass(generateAdapter = true)
    data class Actor(
        @Json(name = "id")
        val id: Long,
        @Json(name = "name")
        val name: String,
        @Json(name = "character")
        val character: String,
        @Json(name = "profile_path")
        val profilePath: String?,
        @Json(name = "order")
        val order: Int,
        @Json(name = "popularity")
        val popularity: Float
    )

    @JsonClass(generateAdapter = true)
    data class CrewMember(
        @Json(name = "id")
        val id: Long,
        @Json(name = "name")
        val name: String,
        @Json(name = "job")
        val job: String,
        @Json(name = "department")
        val department: String,
        @Json(name = "profile_path")
        val profilePath: String?,
        @Json(name = "popularity")
        val popularity: Float
    )
}
