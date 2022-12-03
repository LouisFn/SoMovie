package com.louisfn.somovie.data.network.body

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddToWatchlistBody(
    @Json(name = "media_type")
    val mediaType: String = MOVIE_MEDIA_TYPE,
    @Json(name = "media_id")
    val movieId: Long,
    @Json(name = "watchlist")
    val watchlist: Boolean
) {
    companion object {
        private const val MOVIE_MEDIA_TYPE = "movie"
    }
}
