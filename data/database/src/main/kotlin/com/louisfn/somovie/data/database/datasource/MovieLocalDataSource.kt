package com.louisfn.somovie.data.database.datasource

import androidx.annotation.AnyThread
import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.louisfn.somovie.core.common.annotation.IoDispatcher
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.entity.ExploreEntity
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.database.entity.MovieGenreCrossRefEntity
import com.louisfn.somovie.data.database.entity.MovieProductionCompanyCrossRefEntity
import com.louisfn.somovie.data.database.relation.MovieWithRelations
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface MovieLocalDataSource {

    @AnyThread
    fun moviesChanges(category: ExploreEntity.Category, limit: Int): Flow<List<MovieEntity>>

    @AnyThread
    fun movieChanges(movieId: Long): Flow<MovieWithRelations>

    @AnyThread
    fun getPagingMovies(category: ExploreEntity.Category): PagingSource<Int, MovieEntity>

    @AnyThread
    suspend fun insertOrIgnoreMovies(category: ExploreEntity.Category, movies: List<MovieEntity>, page: Int)

    @AnyThread
    suspend fun insertOrUpdateMovie(movieWithRelations: MovieWithRelations)

    @AnyThread
    suspend fun deleteExploreMovies(category: ExploreEntity.Category)
}

internal class DefaultMovieLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieLocalDataSource {

    override fun moviesChanges(category: ExploreEntity.Category, limit: Int): Flow<List<MovieEntity>> =
        database.movieDao().changes(category, limit)

    override fun movieChanges(movieId: Long): Flow<MovieWithRelations> =
        database.movieDao().movieWithRelationChanges(movieId)

    override fun getPagingMovies(category: ExploreEntity.Category): PagingSource<Int, MovieEntity> =
        database.movieDao().getPaging(category)

    override suspend fun insertOrUpdateMovie(movieWithRelations: MovieWithRelations) = ioDispatcher {
        val movieId = movieWithRelations.movie.id
        with(database) {
            withTransaction {
                movieDao().insertOrUpdate(movieWithRelations.movie)
                genreDao().insertOrIgnore(movieWithRelations.genres)
                companyDao().insertOrIgnore(movieWithRelations.productionCompanies)

                with(productionCountryDao()) {
                    delete(movieId)
                    insertOrAbort(movieWithRelations.productionCountries)
                }

                with(movieGenreCrossRefDao()) {
                    delete(movieId)
                    insertOrAbort(
                        movieWithRelations.genres.map {
                            MovieGenreCrossRefEntity(
                                movieId = movieId,
                                genreId = it.id,
                            )
                        },
                    )
                }

                with(movieProductionCompanyCrossRefDao()) {
                    delete(movieId)
                    insertOrAbort(
                        movieWithRelations.productionCompanies.map {
                            MovieProductionCompanyCrossRefEntity(
                                movieId = movieId,
                                companyId = it.id,
                            )
                        },
                    )
                }
            }
        }
    }

    override suspend fun insertOrIgnoreMovies(category: ExploreEntity.Category, movies: List<MovieEntity>, page: Int) {
        with(database) {
            withTransaction {
                movieDao().insertOrIgnore(movies)
                exploreDao().insertOrIgnore(
                    movies.map {
                        ExploreEntity(
                            movieId = it.id,
                            category = category,
                            page = page,
                        )
                    },
                )
            }
        }
    }

    override suspend fun deleteExploreMovies(category: ExploreEntity.Category) =
        database.exploreDao().delete(category)
}
