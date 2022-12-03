package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.model.YoutubeVideo
import kotlinx.coroutines.flow.Flow

interface MovieVideoRepository {

    @AnyThread
    fun youtubeVideosChanges(movieId: Long): Flow<List<YoutubeVideo>>

    @AnyThread
    suspend fun refreshYoutubeVideos(movieId: Long)
}
