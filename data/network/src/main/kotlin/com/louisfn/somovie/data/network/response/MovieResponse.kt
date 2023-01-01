package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class MovieResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: LocalDate?,
    @Json(name = "vote_average")
    val voteAverage: Float
)
