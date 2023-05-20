package com.louisfn.somovie.domain.usecase.movie

import androidx.annotation.AnyThread
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.data.repository.MovieCreditsRepository
import com.louisfn.somovie.data.repository.MovieImageRepository
import com.louisfn.somovie.data.repository.MovieRepository
import com.louisfn.somovie.data.repository.MovieVideoRepository
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieImageRepository: MovieImageRepository,
    private val movieCreditsRepository: MovieCreditsRepository,
    private val movieVideoRepository: MovieVideoRepository
) {

    @AnyThread
    fun movieChanges(movieId: Long): Flow<Movie> = movieRepository.movieChanges(movieId)

    @AnyThread
    fun moviesPagingChanges(category: ExploreCategory): Flow<PagingData<Movie>> =
        movieRepository.moviesPagingChanges(category, PAGING_CONFIG)

    @AnyThread
    suspend fun refreshMovie(movieId: Long) {
        coroutineScope {
            launch { movieRepository.refreshMovie(movieId) }
            launch { movieImageRepository.refreshMovieImages(movieId) }
            launch { movieCreditsRepository.refreshMovieCredits(movieId) }
            launch { movieVideoRepository.refreshYoutubeVideos(movieId) }
        }
    }

    //region Explore

    @AnyThread
    fun exploreMoviesChanges(): Flow<List<Pair<ExploreCategory, List<Movie>>>> =
        ExploreCategory.values()
            .let { categories ->
                val flows = categories.map {
                    movieRepository.moviesChanges(
                        category = it,
                        limit = LIMIT_BY_CATEGORY
                    )
                }

                combine(
                    flows = flows,
                    transform = { it.mapIndexed { index, list -> categories[index] to list } }
                )
            }

    @AnyThread
    suspend fun refreshExploreMovies() {
        coroutineScope {
            ExploreCategory.values()
                .forEach {
                    launch { movieRepository.refreshMovies(it) }
                }
        }
    }

    //endregion

    companion object {
        private const val LIMIT_BY_CATEGORY = 20
        private val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = true
        )
    }
}
