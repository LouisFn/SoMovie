package com.louisfn.somovie.domain.usecase.video

import com.louisfn.somovie.data.repository.MovieVideoRepository
import com.louisfn.somovie.domain.model.YoutubeVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieVideosInteractor @Inject constructor(
    private val movieVideoRepository: MovieVideoRepository,
) {

    fun movieVideosYoutubeChanges(movieId: Long): Flow<List<YoutubeVideo>> =
        movieVideoRepository.youtubeVideosChanges(movieId)
            .map { videos ->
                videos.sortedWith(
                    compareBy<YoutubeVideo> { it.type.order }.thenByDescending(YoutubeVideo::publishedAt),
                )
            }
}
