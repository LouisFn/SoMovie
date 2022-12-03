package com.louisfn.somovie.data.network.parameter

import com.louisfn.somovie.domain.model.MovieDiscoverSortBy
import com.louisfn.somovie.domain.model.MovieDiscoverSortByDirection

internal class MovieDiscoverSortParameter(
    private val sortBy: MovieDiscoverSortBy,
    private val direction: MovieDiscoverSortByDirection
) {

    override fun toString(): String =
        "${toJson(sortBy)}.${toJson(direction)}"

    private fun toJson(sortBy: MovieDiscoverSortBy) = when (sortBy) {
        MovieDiscoverSortBy.POPULARITY -> POPULARITY
        MovieDiscoverSortBy.RELEASE_DATE -> RELEASE_DATE
        MovieDiscoverSortBy.REVENUE -> REVENUE
        MovieDiscoverSortBy.PRIMARY_RELEASE_DATE -> PRIMARY_RELEASE_DATE
        MovieDiscoverSortBy.ORIGINAL_TITLE -> ORIGINAL_TITLE
        MovieDiscoverSortBy.VOTE_AVERAGE -> VOTE_AVERAGE
        MovieDiscoverSortBy.VOTE_COUNT -> VOTE_COUNT
    }

    private fun toJson(direction: MovieDiscoverSortByDirection): String =
        when (direction) {
            MovieDiscoverSortByDirection.ASC -> ASC
            MovieDiscoverSortByDirection.DESC -> DESC
        }

    companion object {
        private const val POPULARITY = "popularity"
        private const val RELEASE_DATE = "release_date"
        private const val REVENUE = "revenue"
        private const val PRIMARY_RELEASE_DATE = "primary_release_date"
        private const val ORIGINAL_TITLE = "original_title"
        private const val VOTE_AVERAGE = "vote_average"
        private const val VOTE_COUNT = "vote_count"

        private const val ASC = "asc"
        private const val DESC = "desc"
    }
}
