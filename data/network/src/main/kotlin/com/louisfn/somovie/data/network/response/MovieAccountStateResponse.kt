package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieAccountStateResponse(
    @Json(name = "id")
    val movieId: Long,
    @Json(name = "watchlist")
    val watchlist: Boolean,
    @Json(name = "favorite")
    val favorite: Boolean,
)
