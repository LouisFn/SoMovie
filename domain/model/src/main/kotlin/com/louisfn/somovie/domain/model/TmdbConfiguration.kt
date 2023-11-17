package com.louisfn.somovie.domain.model

import java.time.Instant

data class TmdbConfiguration(
    val images: Images?,
    val changesKeys: List<String>?,
    val updatedAt: Instant?,
) {

    data class Images(
        val baseUrl: String,
        val secureBaseUrl: String,
        val backdropSizes: List<String>,
        val logoSizes: List<String>,
        val posterSizes: List<String>,
        val profileSizes: List<String>,
        val stillSizes: List<String>,
    )

    val isFetched = updatedAt != null
}
