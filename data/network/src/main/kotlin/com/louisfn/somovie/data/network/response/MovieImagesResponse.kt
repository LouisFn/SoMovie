package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieImagesResponse(
    @Json(name = "id")
    val movieId: Long,
    @Json(name = "backdrops")
    val backdrops: List<Image>,
    @Json(name = "posters")
    val posters: List<Image>,
) {

    @JsonClass(generateAdapter = true)
    data class Image(
        @Json(name = "file_path")
        val filePath: String,
        @Json(name = "width")
        val width: Int,
    )
}
