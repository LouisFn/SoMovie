package com.louisfn.somovie.domain.model

import androidx.annotation.FloatRange
import java.time.Duration
import java.time.LocalDate

data class Movie(
    val id: Long,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val releaseDate: LocalDate?,
    @FloatRange(from = 0.0, to = 10.0)
    val voteAverage: Float,
    val posterPath: PosterPath?,
    val backdropPath: BackdropPath?,
    val watchlist: Boolean?,
    val details: Details? = null,
) {
    val hasVotes = voteAverage > 0

    val tmdbUrl: String
        get() = "https://www.themoviedb.org/movie/$id"

    data class Details(
        val tagline: String?,
        val genres: List<MovieGenre>,
        val runtime: Duration?,
        val popularity: Float,
        val budget: Int,
        val revenue: Int,
        val productionCountries: List<Country>,
        val productionCompanies: List<Company>,
        val voteCount: Int,
    )
}
