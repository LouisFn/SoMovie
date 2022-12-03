package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.database.DatabaseHelper
import com.louisfn.somovie.data.database.datasource.MovieLocalDataSource
import com.louisfn.somovie.data.database.datasource.RemoteKeyLocalDataSource
import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.data.mapper.ExploreCategoryMapper
import com.louisfn.somovie.data.mapper.MovieMapper
import com.louisfn.somovie.data.network.Constants.PAGINATION_FIRST_PAGE_INDEX
import com.louisfn.somovie.data.network.datasource.MovieRemoteDataSource
import com.louisfn.somovie.data.repository.paging.MoviesRemoteMediator
import com.louisfn.somovie.data.repository.paging.mapPaging
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
import javax.inject.Inject

internal class DefaultMovieRepository @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val mapper: MovieMapper,
    private val categoryMapper: ExploreCategoryMapper,
    private val databaseHelper: DatabaseHelper,
    private val sessionLocalDataSource: DataStoreLocalDataSource<SessionData>,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : MovieRepository {

    //region Explore movies

    override fun moviesPagingChanges(
        category: ExploreCategory,
        pagingConfig: PagingConfig,
        cacheTimeout: Long
    ): Flow<PagingData<Movie>> =
        Pager(
            config = pagingConfig,
            remoteMediator = createMoviesRemoteMediator(category, cacheTimeout),
            pagingSourceFactory = {
                localDataSource.getPagingMovies(categoryMapper.mapToEntity(category))
            }
        )
            .flow
            .mapPaging { mapper.mapToDomain(it) }
            .flowOn(defaultDispatcher)

    @AnyThread
    private fun createMoviesRemoteMediator(
        category: ExploreCategory,
        cacheTimeout: Long
    ) = MoviesRemoteMediator(
        cacheTimeout = cacheTimeout,
        remoteKeyLocalDataSource = remoteKeyLocalDataSource,
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource,
        movieMapper = mapper,
        categoryMapper = categoryMapper,
        databaseHelper = databaseHelper,
        category = category
    )

    override fun moviesChanges(category: ExploreCategory, limit: Int): Flow<List<Movie>> =
        localDataSource.moviesChanges(categoryMapper.mapToEntity(category), limit)
            .map(mapper::mapToDomain)
            .flowOn(defaultDispatcher)

    override suspend fun refreshMovies(category: ExploreCategory, cacheTimeout: Long) =
        defaultDispatcher {
            val remoteKeyTypeEntity = categoryMapper.mapToRemoteKeyTypeEntity(category)
            if (!remoteKeyLocalDataSource.isExpired(remoteKeyTypeEntity, cacheTimeout)) {
                return@defaultDispatcher
            }

            val response = remoteDataSource.getMovies(category, PAGINATION_FIRST_PAGE_INDEX).results

            val categoryEntity = categoryMapper.mapToEntity(category)
            databaseHelper.withTransaction {
                with(localDataSource) {
                    deleteExploreMovies(categoryEntity)
                    insertOrIgnoreMovies(
                        category = categoryEntity,
                        movies = mapper.mapToEntity(response, false),
                        page = PAGINATION_FIRST_PAGE_INDEX
                    )
                }
                remoteKeyLocalDataSource.updateNextKey(
                    type = remoteKeyTypeEntity,
                    nextKey = (PAGINATION_FIRST_PAGE_INDEX + 1).toString(),
                    reset = true
                )
            }
        }

    //endregion

    //region Movie

    override fun movieChanges(movieId: Long): Flow<Movie> =
        localDataSource.movieChanges(movieId)
            .map(mapper::mapToDomain)
            .flowOn(defaultDispatcher)

    override suspend fun refreshMovie(movieId: Long) {
        defaultDispatcher {
            val detailsDeferred = async { remoteDataSource.getMovieDetails(movieId) }
            val accountStateDeferred = async {
                if (sessionLocalDataSource.getData().account != null) {
                    remoteDataSource.getMovieAccountStates(movieId)
                } else {
                    null
                }
            }
            localDataSource.insertOrUpdateMovie(
                mapper.mapToEntity(
                    detailsResponse = detailsDeferred.await(),
                    accountStateResponse = accountStateDeferred.await()
                )
            )
        }
    }

    //endregion
}
