package com.louisfn.somovie.data.database.datasource

import androidx.annotation.AnyThread
import androidx.room.withTransaction
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.entity.MovieImageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieImageLocalDataSource {

    @AnyThread
    fun movieImagesChanges(movieId: Long): Flow<List<MovieImageEntity>>

    @AnyThread
    suspend fun replaceMovieImages(movieId: Long, movieImages: List<MovieImageEntity>)
}

internal class DefaultMovieImageLocalDataSource @Inject constructor(
    private val database: AppDatabase
) : MovieImageLocalDataSource {

    override fun movieImagesChanges(movieId: Long): Flow<List<MovieImageEntity>> =
        database.movieImageDao().changes(movieId)

    override suspend fun replaceMovieImages(movieId: Long, movieImages: List<MovieImageEntity>) {
        with(database) {
            withTransaction {
                with(movieImageDao()) {
                    delete(movieId)
                    insertOrAbort(movieImages)
                }
            }
        }
    }
}
