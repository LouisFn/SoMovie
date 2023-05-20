package com.louisfn.somovie.domain.usecase.movie

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.repository.MovieCreditsRepository
import com.louisfn.somovie.domain.model.MovieCredits
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieCreditsInteractor @Inject constructor(
    private val movieCreditsRepository: MovieCreditsRepository
) {
    @AnyThread
    fun movieCreditsChanges(movieId: Long): Flow<MovieCredits> =
        movieCreditsRepository.movieCreditsChanges(movieId)
}
