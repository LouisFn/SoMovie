package com.louisfn.somovie.test.testfixtures.android.data.domain

import com.louisfn.somovie.domain.model.TmdbConfiguration
import java.time.LocalDate
import java.time.ZoneOffset

object FakeTmdbConfigurationFactory {

    const val secureBaseUrl = "https://image.tmdb.org/t/p/"

    val default = TmdbConfiguration(
        images = TmdbConfiguration.Images(
            baseUrl = "http://image.tmdb.org/t/p/",
            secureBaseUrl = secureBaseUrl,
            backdropSizes = listOf("w300", "w780", "w1280", "original"),
            logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
            posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
            profileSizes = listOf("w45", "w185", "h632", "original"),
            stillSizes = listOf("w92", "w185", "w300", "original")
        ),
        changesKeys = listOf(
            "adult",
            "air_date",
            "also_known_as",
            "alternative_titles",
            "biography",
            "birthday",
            "budget",
            "cast",
            "certifications",
            "character_names",
            "created_by",
            "crew",
            "deathday",
            "episode",
            "episode_number",
            "episode_run_time",
            "freebase_id",
            "freebase_mid",
            "general",
            "genres",
            "guest_stars",
            "homepage",
            "images",
            "imdb_id",
            "languages",
            "name",
            "network",
            "origin_country",
            "original_name",
            "original_title",
            "overview",
            "parts",
            "place_of_birth",
            "plot_keywords",
            "production_code",
            "production_companies",
            "production_countries",
            "releases",
            "revenue",
            "runtime",
            "season",
            "season_number",
            "season_regular",
            "spoken_languages",
            "status",
            "tagline",
            "title",
            "translations",
            "tvdb_id",
            "tvrage_id",
            "type",
            "video",
            "videos"
        ),
        updatedAt = LocalDate.of(2022, 8, 1).atStartOfDay().toInstant(ZoneOffset.UTC)
    )

    val empty = TmdbConfiguration(
        images = null,
        changesKeys = null,
        updatedAt = null
    )
}
