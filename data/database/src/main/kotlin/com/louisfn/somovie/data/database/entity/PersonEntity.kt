package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.*

@Entity(tableName = TABLE_PERSON)
data class PersonEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_PROFILE_PATH)
    val profilePath: String?,
    @ColumnInfo(name = COLUMN_POPULARITY)
    val popularity: Float,
)
