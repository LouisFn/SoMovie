package com.louisfn.somovie.data.database.entity

import androidx.room.*
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.TABLE_WATCHLIST

@Entity(
    tableName = TABLE_WATCHLIST,
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_MOVIE_ID),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(COLUMN_FK_MOVIE_ID, unique = true)]
)
data class WatchlistEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,
    @ColumnInfo(name = COLUMN_FK_MOVIE_ID)
    val movieId: Long
)
