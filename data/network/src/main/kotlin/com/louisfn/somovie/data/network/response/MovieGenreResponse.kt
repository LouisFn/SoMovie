package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieGenreResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "name")
    val name: String,
)
