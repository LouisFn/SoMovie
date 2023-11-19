package com.louisfn.somovie.data.repository.paging

import com.louisfn.somovie.data.database.DatabaseHelper
import com.louisfn.somovie.data.database.datasource.RemoteKeyLocalDataSource
import com.louisfn.somovie.data.database.datasource.WatchlistLocalDataSource
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity.Type.WATCH_LIST
import com.louisfn.somovie.data.mapper.MovieMapper
import com.louisfn.somovie.data.network.Constants
import com.louisfn.somovie.data.network.datasource.WatchlistRemoteDataSource

internal class WatchlistRemoteMediator(
    cacheTimeout: Long,
    remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val localDataSource: WatchlistLocalDataSource,
    private val remoteDataSource: WatchlistRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val databaseHelper: DatabaseHelper,
    private val accountId: Long,
) : DefaultAppendRemoteMediator<Int, MovieEntity>(
    remoteKeyType = WATCH_LIST,
    remoteKeyLocalDataSource = remoteKeyLocalDataSource,
    cacheTimeout = cacheTimeout,
) {

    override suspend fun fetchNewData(key: String?): Result {
        val page = key?.toInt() ?: Constants.PAGINATION_FIRST_PAGE_INDEX

        val response = remoteDataSource.getWatchList(accountId, page)
        val movieEntities = movieMapper.mapToEntity(response.results, true)

        databaseHelper.withTransaction {
            with(localDataSource) {
                if (key == null) {
                    deleteWatchlist()
                }
                insertOrIgnoreToWatchlist(movieEntities)
            }
        }

        return Result(
            nextKey = (page + 1).toString(),
            endOfPaginationReached = response.totalPages == page,
        )
    }
}
