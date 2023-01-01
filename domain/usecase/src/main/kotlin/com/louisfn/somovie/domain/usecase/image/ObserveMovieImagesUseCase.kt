package com.louisfn.somovie.domain.usecase.image

import com.louisfn.somovie.data.repository.MovieImageRepository
import com.louisfn.somovie.domain.model.MovieImages
import com.louisfn.somovie.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMovieImagesUseCase @Inject constructor(
    private val movieImageRepository: MovieImageRepository
) : FlowUseCase<Long, MovieImages>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun execute(movieId: Long): Flow<MovieImages> =
        movieImageRepository.movieImagesChanges(movieId)
}
