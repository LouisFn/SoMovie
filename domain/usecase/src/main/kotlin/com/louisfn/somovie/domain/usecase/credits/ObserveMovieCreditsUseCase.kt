package com.louisfn.somovie.domain.usecase.credits

import com.louisfn.somovie.data.repository.MovieCreditsRepository
import com.louisfn.somovie.domain.model.MovieCredits
import com.louisfn.somovie.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMovieCreditsUseCase @Inject constructor(
    private val movieCreditsRepository: MovieCreditsRepository
) : FlowUseCase<Long, MovieCredits>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun execute(movieId: Long): Flow<MovieCredits> =
        movieCreditsRepository.movieCreditsChanges(movieId)
}
