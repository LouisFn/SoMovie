package com.louisfn.somovie.data.repository.paging

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.louisfn.somovie.common.logger.Logger
import com.louisfn.somovie.data.database.datasource.RemoteKeyLocalDataSource
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity

internal abstract class DefaultAppendRemoteMediator<Key : Any, Value : Any> constructor(
    private val remoteKeyLocalDataSource: RemoteKeyLocalDataSource,
    private val remoteKeyType: RemoteKeyEntity.Type,
    private val cacheTimeout: Long
) : RemoteMediator<Key, Value>() {

    override suspend fun initialize() =
        if (remoteKeyLocalDataSource.isExpired(remoteKeyType, cacheTimeout)) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }

    @Suppress("ReturnCount")
    override suspend fun load(loadType: LoadType, state: PagingState<Key, Value>): MediatorResult {
        return try {
            val remoteKey = remoteKeyLocalDataSource.getRemoteKey(remoteKeyType)

            val key = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> checkNotNull(remoteKey).nextKey
                    ?: return MediatorResult.Success(true)
            }

            val result = fetchNewData(key)

            remoteKeyLocalDataSource.updateNextKey(
                type = remoteKeyType,
                nextKey = result.nextKey,
                reset = loadType == LoadType.REFRESH
            )

            MediatorResult.Success(endOfPaginationReached = result.endOfPaginationReached)
        } catch (e: Exception) {
            Logger.e(e)
            MediatorResult.Error(e)
        }
    }

    abstract suspend fun fetchNewData(key: String?): Result

    data class Result(
        val nextKey: String,
        val endOfPaginationReached: Boolean
    )
}
