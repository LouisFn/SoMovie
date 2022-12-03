package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.model.MovieCredits
import kotlinx.coroutines.flow.Flow

interface MovieCreditsRepository {

    @AnyThread
    fun movieCreditsChanges(movieId: Long): Flow<MovieCredits>

    @AnyThread
    suspend fun refreshMovieCredits(movieId: Long)
}
