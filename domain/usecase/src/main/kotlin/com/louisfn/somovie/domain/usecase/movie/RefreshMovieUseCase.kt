package com.louisfn.somovie.domain.usecase.movie

import com.louisfn.somovie.data.repository.MovieCreditsRepository
import com.louisfn.somovie.data.repository.MovieImageRepository
import com.louisfn.somovie.data.repository.MovieRepository
import com.louisfn.somovie.data.repository.MovieVideoRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RefreshMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieImageRepository: MovieImageRepository,
    private val movieCreditsRepository: MovieCreditsRepository,
    private val movieVideoRepository: MovieVideoRepository
) : SuspendUseCase<Long, Unit>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override suspend fun execute(movieId: Long) {
        coroutineScope {
            launch { movieRepository.refreshMovie(movieId) }
            launch { movieImageRepository.refreshMovieImages(movieId) }
            launch { movieCreditsRepository.refreshMovieCredits(movieId) }
            launch { movieVideoRepository.refreshYoutubeVideos(movieId) }
        }
    }
}
