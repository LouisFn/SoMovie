package com.louisfn.somovie.domain.usecase.movie

import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.repository.MovieRepository
import com.louisfn.somovie.domain.usecase.SuspendUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RefreshExploreMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : SuspendUseCase<Unit, Unit>() {

    override suspend fun execute(parameter: Unit) = coroutineScope {
        ExploreCategory.values()
            .forEach {
                launch { movieRepository.refreshMovies(it) }
            }
    }
}
