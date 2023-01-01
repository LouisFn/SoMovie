package com.louisfn.somovie.test.fixtures.datasource.database

import com.louisfn.somovie.core.common.extension.isExpired
import com.louisfn.somovie.data.database.datasource.RemoteKeyLocalDataSource
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity
import com.louisfn.somovie.test.fixtures.utils.FakeDateTimeProvider
import java.util.concurrent.CopyOnWriteArrayList

class FakeRemoteKeyLocalDataSource(val dateTimeProvider: FakeDateTimeProvider = FakeDateTimeProvider()) :
    RemoteKeyLocalDataSource {

    private val remoteKeys = CopyOnWriteArrayList<RemoteKeyEntity>()

    override suspend fun getRemoteKey(type: RemoteKeyEntity.Type): RemoteKeyEntity? =
        remoteKeys.firstOrNull { it.type == type }

    override suspend fun isExpired(type: RemoteKeyEntity.Type, cacheTimeout: Long): Boolean =
        getRemoteKey(type)?.firstFetchAt.isExpired(dateTimeProvider.now(), cacheTimeout)

    override suspend fun updateNextKey(
        type: RemoteKeyEntity.Type,
        nextKey: String,
        reset: Boolean
    ) {
        if (!reset) {
            val remoteKey = remoteKeys.first { it.type == type }
            val index = remoteKeys.indexOf(remoteKey)
            remoteKeys[index] = remoteKey.copy(nextKey = nextKey)
        } else {
            remoteKeys.add(RemoteKeyEntity(type, nextKey, dateTimeProvider.now()))
        }
    }
}
