package com.louisfn.somovie.domain.usecase.movie

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.repository.MovieImageRepository
import com.louisfn.somovie.domain.model.MovieImages
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieImageInteractor @Inject constructor(
    private val movieImageRepository: MovieImageRepository
) {

    @AnyThread
    fun movieImagesChanges(movieId: Long): Flow<MovieImages> =
        movieImageRepository.movieImagesChanges(movieId)
}
