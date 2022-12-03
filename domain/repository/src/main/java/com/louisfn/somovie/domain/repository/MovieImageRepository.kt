package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.model.MovieImages
import kotlinx.coroutines.flow.Flow

interface MovieImageRepository {

    @AnyThread
    fun movieImagesChanges(movieId: Long): Flow<MovieImages>

    @AnyThread
    suspend fun refreshMovieImages(movieId: Long)
}
