package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_NEXT_KEY
import com.louisfn.somovie.data.database.COLUMN_TYPE
import com.louisfn.somovie.data.database.TABLE_REMOTE_KEY
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity

@Dao
internal abstract class RemoteKeyDao : BaseDao<RemoteKeyEntity>(TABLE_REMOTE_KEY) {

    @Query("SELECT * FROM $TABLE_REMOTE_KEY WHERE $COLUMN_TYPE = :type")
    abstract suspend fun get(type: RemoteKeyEntity.Type): RemoteKeyEntity?

    @Query("DELETE FROM $TABLE_REMOTE_KEY WHERE $COLUMN_TYPE = :type")
    abstract suspend fun delete(type: RemoteKeyEntity.Type)

    @Query("UPDATE $TABLE_REMOTE_KEY SET $COLUMN_NEXT_KEY = :nextKey WHERE $COLUMN_TYPE = :type")
    abstract suspend fun updateNextKey(type: RemoteKeyEntity.Type, nextKey: String)
}
