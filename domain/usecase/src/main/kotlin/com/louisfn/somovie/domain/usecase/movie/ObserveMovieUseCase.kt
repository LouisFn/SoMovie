package com.louisfn.somovie.domain.usecase.movie

import com.louisfn.somovie.data.repository.MovieRepository
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : FlowUseCase<Long, Movie>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun execute(movieId: Long): Flow<Movie> =
        movieRepository.movieChanges(movieId)
}
