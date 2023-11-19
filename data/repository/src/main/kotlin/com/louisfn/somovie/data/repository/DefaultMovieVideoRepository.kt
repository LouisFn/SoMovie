package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.database.datasource.MovieVideoLocalDataSource
import com.louisfn.somovie.data.mapper.YoutubeVideoMapper
import com.louisfn.somovie.data.network.datasource.MovieVideoRemoteDataSource
import com.louisfn.somovie.domain.model.YoutubeVideo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface MovieVideoRepository {

    @AnyThread
    fun youtubeVideosChanges(movieId: Long): Flow<List<YoutubeVideo>>

    @AnyThread
    suspend fun refreshYoutubeVideos(movieId: Long)
}

internal class DefaultMovieVideoRepository @Inject constructor(
    private val localDataSource: MovieVideoLocalDataSource,
    private val remoteDataSource: MovieVideoRemoteDataSource,
    private val youtubeVideoMapper: YoutubeVideoMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : MovieVideoRepository {

    override fun youtubeVideosChanges(movieId: Long): Flow<List<YoutubeVideo>> =
        localDataSource.youtubeVideosChanges(movieId)
            .map(youtubeVideoMapper::mapToDomain)
            .flowOn(defaultDispatcher)

    override suspend fun refreshYoutubeVideos(movieId: Long) {
        defaultDispatcher {
            remoteDataSource.getMovieVideos(movieId)
                .let { youtubeVideoMapper.mapToEntity(movieId, it) }
                .also { localDataSource.replaceYoutubeVideos(movieId, it) }
        }
    }
}
