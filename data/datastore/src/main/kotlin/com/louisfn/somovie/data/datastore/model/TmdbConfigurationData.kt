package com.louisfn.somovie.data.datastore.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Instant

@JsonClass(generateAdapter = true)
data class TmdbConfigurationData(
    @Json(name = "images")
    val images: Images? = null,
    @Json(name = "change_keys")
    val changesKeys: List<String>? = null,
    @Json(name = "updated_at")
    val updatedAt: Instant? = null
) : DataStoreData {

    @JsonClass(generateAdapter = true)
    data class Images(
        @Json(name = "base_url")
        val baseUrl: String,
        @Json(name = "secure_base_url")
        val secureBaseUrl: String,
        @Json(name = "backdrop_sizes")
        val backdropSizes: List<String>,
        @Json(name = "logo_sizes")
        val logoSizes: List<String>,
        @Json(name = "poster_sizes")
        val posterSizes: List<String>,
        @Json(name = "profile_sizes")
        val profileSizes: List<String>,
        @Json(name = "still_sizes")
        val stillSizes: List<String>
    )
}
