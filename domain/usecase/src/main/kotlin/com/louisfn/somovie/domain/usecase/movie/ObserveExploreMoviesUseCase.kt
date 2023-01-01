package com.louisfn.somovie.domain.usecase.movie

import com.louisfn.somovie.data.repository.MovieRepository
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.usecase.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveExploreMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) : FlowUseCase<Unit, List<Pair<ExploreCategory, List<Movie>>>>() {

    override fun execute(parameter: Unit): Flow<List<Pair<ExploreCategory, List<Movie>>>> =
        ExploreCategory.values()
            .let { categories ->
                val flows = categories.map { movieRepository.moviesChanges(it, LIMIT_BY_CATEGORY) }

                combine(
                    flows = flows,
                    transform = { it.mapIndexed { index, list -> categories[index] to list } }
                )
            }

    companion object {
        private const val LIMIT_BY_CATEGORY = 20
    }
}
