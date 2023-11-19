package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.COLUMN_NAME
import com.louisfn.somovie.data.database.TABLE_GENRE

@Entity(tableName = TABLE_GENRE)
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
)
