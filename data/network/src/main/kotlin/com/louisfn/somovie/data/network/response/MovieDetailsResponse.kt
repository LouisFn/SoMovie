package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.Duration
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    @Json(name = "id")
    val id: Long,
    @Json(name = "title")
    val title: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "tagline")
    val tagline: String?,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "release_date")
    val releaseDate: LocalDate,
    @Json(name = "genres")
    val genres: List<MovieGenreResponse>,
    @Json(name = "runtime")
    val runtime: Duration?,
    @Json(name = "vote_average")
    val voteAverage: Float,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "popularity")
    val popularity: Float,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "budget")
    val budget: Int,
    @Json(name = "revenue")
    val revenue: Int,
    @Json(name = "production_countries")
    val productionCountries: List<ProductionCountryResponse>,
    @Json(name = "production_companies")
    val productionCompanies: List<ProductionCompanyResponse>,
) {

    @JsonClass(generateAdapter = true)
    data class ProductionCountryResponse(
        @Json(name = "iso_3166_1")
        val iso31661: String,
        @Json(name = "name")
        val name: String,
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCompanyResponse(
        @Json(name = "id")
        val id: Long,
        @Json(name = "logo_path")
        val logoPath: String?,
        @Json(name = "name")
        val name: String,
    )
}
