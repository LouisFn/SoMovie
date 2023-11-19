package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.database.datasource.MovieImageLocalDataSource
import com.louisfn.somovie.data.mapper.MovieImageMapper
import com.louisfn.somovie.data.network.datasource.MovieImageRemoteDataSource
import com.louisfn.somovie.domain.model.MovieImages
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface MovieImageRepository {

    @AnyThread
    fun movieImagesChanges(movieId: Long): Flow<MovieImages>

    @AnyThread
    suspend fun refreshMovieImages(movieId: Long)
}

internal class DefaultMovieImageRepository @Inject constructor(
    private val remoteDataSource: MovieImageRemoteDataSource,
    private val localDataSource: MovieImageLocalDataSource,
    private val mapper: MovieImageMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : MovieImageRepository {

    override fun movieImagesChanges(movieId: Long): Flow<MovieImages> =
        localDataSource.movieImagesChanges(movieId)
            .map(mapper::mapToDomain)
            .flowOn(defaultDispatcher)

    override suspend fun refreshMovieImages(movieId: Long) {
        defaultDispatcher {
            remoteDataSource.getMovieImages(movieId)
                .let { mapper.mapResponseToEntity(it) }
                .also { localDataSource.replaceMovieImages(movieId, it) }
        }
    }
}
