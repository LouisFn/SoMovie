package com.louisfn.somovie.domain.repository

import androidx.annotation.FloatRange
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.model.MovieDiscoverSortBy
import com.louisfn.somovie.domain.model.MovieDiscoverSortByDirection

interface MovieDiscoverRepository {

    suspend fun getDiscoverMovies(
        sortBy: MovieDiscoverSortBy,
        sortByDirection: MovieDiscoverSortByDirection,
        minVoteCount: Int,
        @FloatRange(from = 0.0, to = 10.0) minVoteAverage: Float,
        page: Int
    ): List<Movie>
}
