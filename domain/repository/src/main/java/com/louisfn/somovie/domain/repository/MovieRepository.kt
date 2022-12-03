package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

interface MovieRepository {

    @AnyThread
    fun moviesPagingChanges(
        category: ExploreCategory,
        pagingConfig: PagingConfig,
        cacheTimeout: Long = DEFAULT_CACHE_TIMEOUT
    ): Flow<PagingData<Movie>>

    @AnyThread
    fun moviesChanges(category: ExploreCategory, limit: Int): Flow<List<Movie>>

    @AnyThread
    suspend fun refreshMovies(category: ExploreCategory, cacheTimeout: Long = DEFAULT_CACHE_TIMEOUT)

    @AnyThread
    fun movieChanges(movieId: Long): Flow<Movie>

    @AnyThread
    suspend fun refreshMovie(movieId: Long)

    companion object {
        private val DEFAULT_CACHE_TIMEOUT = TimeUnit.HOURS.toMillis(12)
    }
}
