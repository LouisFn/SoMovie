package com.louisfn.somovie.data.repository.paging

import com.louisfn.somovie.data.database.DatabaseHelper
import com.louisfn.somovie.data.database.datasource.MovieLocalDataSource
import com.louisfn.somovie.data.database.datasource.RemoteKeyLocalDataSource
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.mapper.ExploreCategoryMapper
import com.louisfn.somovie.data.mapper.MovieMapper
import com.louisfn.somovie.data.network.Constants
import com.louisfn.somovie.data.network.datasource.MovieRemoteDataSource
import com.louisfn.somovie.domain.model.ExploreCategory

internal class MoviesRemoteMediator(
    cacheTimeout: Long,
    remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val categoryMapper: ExploreCategoryMapper,
    private val databaseHelper: DatabaseHelper,
    private val category: ExploreCategory
) : DefaultAppendRemoteMediator<Int, MovieEntity>(
    remoteKeyType = categoryMapper.mapToRemoteKeyTypeEntity(category),
    remoteKeyLocalDataSource = remoteKeyLocalDataSource,
    cacheTimeout = cacheTimeout
) {

    override suspend fun fetchNewData(key: String?): Result {
        val page = key?.toInt() ?: Constants.PAGINATION_FIRST_PAGE_INDEX
        val response = remoteDataSource.getMovies(category, page)

        val categoryEntity = categoryMapper.mapToEntity(category)
        val movieEntities = movieMapper.mapToEntity(response.results, false)

        databaseHelper.withTransaction {
            with(localDataSource) {
                if (key == null) {
                    deleteExploreMovies(categoryEntity)
                }
                insertOrIgnoreMovies(categoryEntity, movieEntities, page)
            }
        }

        return Result(
            nextKey = (page + 1).toString(),
            endOfPaginationReached = response.totalPages == page
        )
    }
}
