package com.louisfn.somovie.domain.usecase.video

import com.louisfn.somovie.data.repository.MovieVideoRepository
import com.louisfn.somovie.domain.model.YoutubeVideo
import com.louisfn.somovie.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveMovieYoutubeVideosUseCase @Inject constructor(
    private val movieVideoRepository: MovieVideoRepository
) : FlowUseCase<Long, List<YoutubeVideo>>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun execute(movieId: Long): Flow<List<YoutubeVideo>> =
        movieVideoRepository.youtubeVideosChanges(movieId)
            .map { videos ->
                videos.sortedWith(
                    compareBy<YoutubeVideo> { it.type.order }.thenByDescending(YoutubeVideo::publishedAt)
                )
            }
}
