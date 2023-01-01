package com.louisfn.somovie.data.database.datasource

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.extension.isExpired
import com.louisfn.somovie.core.common.provider.DateTimeProvider
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity
import javax.inject.Inject

interface RemoteKeyLocalDataSource {

    @AnyThread
    suspend fun getRemoteKey(type: RemoteKeyEntity.Type): RemoteKeyEntity?

    @AnyThread
    suspend fun isExpired(type: RemoteKeyEntity.Type, cacheTimeout: Long): Boolean

    @AnyThread
    suspend fun updateNextKey(type: RemoteKeyEntity.Type, nextKey: String, reset: Boolean)
}

internal class DefaultRemoteKeyLocalDataSource @Inject constructor(
    private val database: AppDatabase,
    private val dateTimeProvider: DateTimeProvider
) : RemoteKeyLocalDataSource {

    override suspend fun getRemoteKey(type: RemoteKeyEntity.Type): RemoteKeyEntity? =
        database.remoteKeyDao().get(type)

    override suspend fun isExpired(type: RemoteKeyEntity.Type, cacheTimeout: Long): Boolean =
        getRemoteKey(type)?.firstFetchAt.isExpired(dateTimeProvider.now(), cacheTimeout)

    override suspend fun updateNextKey(type: RemoteKeyEntity.Type, nextKey: String, reset: Boolean) {
        with(database.remoteKeyDao()) {
            if (reset) {
                insertOrReplace(
                    RemoteKeyEntity(
                        type = type,
                        nextKey = nextKey,
                        firstFetchAt = dateTimeProvider.now()
                    )
                )
            } else {
                updateNextKey(type, nextKey)
            }
        }
    }
}
