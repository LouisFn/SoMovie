package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.COLUMN_FIRST_FETCH_AT
import com.louisfn.somovie.data.database.COLUMN_NEXT_KEY
import com.louisfn.somovie.data.database.COLUMN_TYPE
import com.louisfn.somovie.data.database.TABLE_REMOTE_KEY
import java.time.Instant

@Entity(tableName = TABLE_REMOTE_KEY)
data class RemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_TYPE)
    val type: Type,
    @ColumnInfo(name = COLUMN_NEXT_KEY)
    val nextKey: String?,
    @ColumnInfo(name = COLUMN_FIRST_FETCH_AT)
    val firstFetchAt: Instant
) {

    enum class Type(val value: Int) {
        EXPLORE_POPULAR(0),
        EXPLORE_NOW_PLAYING(1),
        EXPLORE_TOP_RATED(2),
        EXPLORE_UPCOMING(3),
        WATCH_LIST(4)
    }
}
