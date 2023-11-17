package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import androidx.annotation.FloatRange
import com.louisfn.somovie.data.mapper.MovieMapper
import com.louisfn.somovie.data.network.datasource.MovieDiscoverRemoteDataSource
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.model.MovieDiscoverSortBy
import com.louisfn.somovie.domain.model.MovieDiscoverSortByDirection
import javax.inject.Inject

interface MovieDiscoverRepository {

    @AnyThread
    suspend fun getDiscoverMovies(
        sortBy: MovieDiscoverSortBy,
        sortByDirection: MovieDiscoverSortByDirection,
        minVoteCount: Int,
        @FloatRange(from = 0.0, to = 10.0) minVoteAverage: Float,
        page: Int,
    ): List<Movie>
}

internal class DefaultMovieDiscoverRepository @Inject constructor(
    private val remoteDataSource: MovieDiscoverRemoteDataSource,
    private val movieMapper: MovieMapper,
) : MovieDiscoverRepository {

    override suspend fun getDiscoverMovies(
        sortBy: MovieDiscoverSortBy,
        sortByDirection: MovieDiscoverSortByDirection,
        minVoteCount: Int,
        @FloatRange(from = 0.0, to = 10.0) minVoteAverage: Float,
        page: Int,
    ): List<Movie> =
        remoteDataSource.getMovieDiscover(
            sortBy = sortBy,
            sortByDirection = sortByDirection,
            minVoteCount = minVoteCount,
            minVoteAverage = minVoteAverage,
            page = page,
        )
            .results
            .let { movieMapper.mapToDomain(it) }
}
