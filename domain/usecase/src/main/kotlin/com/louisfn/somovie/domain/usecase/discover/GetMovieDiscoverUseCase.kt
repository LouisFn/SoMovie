package com.louisfn.somovie.domain.usecase.discover

import com.louisfn.somovie.data.repository.MovieDiscoverRepository
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.model.MovieDiscoverSortBy
import com.louisfn.somovie.domain.model.MovieDiscoverSortByDirection
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import javax.inject.Inject
import kotlin.random.Random

class GetMovieDiscoverUseCase @Inject constructor(
    private val movieDiscoverRepository: MovieDiscoverRepository
) : SuspendUseCase<Unit, List<Movie>>() {

    override suspend fun execute(parameter: Unit): List<Movie> =
        movieDiscoverRepository.getDiscoverMovies(
            sortBy = MovieDiscoverSortBy.POPULARITY,
            sortByDirection = MovieDiscoverSortByDirection.DESC,
            minVoteCount = MIN_VOTE_COUNT,
            minVoteAverage = MIN_VOTE_AVERAGE,
            page = Random.nextInt(1, MAX_PAGE)
        )

    // Hardcoded values to simulate a list of pseudo-random discoveries
    companion object {
        private const val MIN_VOTE_COUNT = 1000
        private const val MIN_VOTE_AVERAGE = 3f
        private const val MAX_PAGE = 150
    }
}
