package com.louisfn.somovie.domain.usecase.movie

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.repository.MovieRepository
import com.louisfn.somovie.domain.usecase.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePagedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : PagingUseCase<ExploreCategory, Movie>() {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun execute(category: ExploreCategory): Flow<PagingData<Movie>> =
        movieRepository.moviesPagingChanges(category, PAGING_CONFIG)

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            prefetchDistance = 10,
            enablePlaceholders = true
        )
    }
}
