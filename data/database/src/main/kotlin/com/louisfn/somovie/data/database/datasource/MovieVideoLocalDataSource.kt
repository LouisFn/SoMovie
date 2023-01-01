package com.louisfn.somovie.data.database.datasource

import androidx.annotation.AnyThread
import androidx.room.withTransaction
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.entity.YoutubeVideoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieVideoLocalDataSource {

    @AnyThread
    fun youtubeVideosChanges(movieId: Long): Flow<List<YoutubeVideoEntity>>

    @AnyThread
    suspend fun replaceYoutubeVideos(movieId: Long, youtubeVideos: List<YoutubeVideoEntity>)
}

internal class DefaultMovieVideoLocalDataSource @Inject constructor(
    private val database: AppDatabase
) : MovieVideoLocalDataSource {

    override fun youtubeVideosChanges(movieId: Long): Flow<List<YoutubeVideoEntity>> =
        database.youtubeVideoDao()
            .changes(movieId)

    override suspend fun replaceYoutubeVideos(movieId: Long, youtubeVideos: List<YoutubeVideoEntity>) {
        with(database) {
            withTransaction {
                with(youtubeVideoDao()) {
                    delete(movieId)
                    insertOrAbort(youtubeVideos)
                }
            }
        }
    }
}
