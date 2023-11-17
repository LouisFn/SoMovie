package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.*

@Entity(
    tableName = TABLE_MOVIE_IMAGE,
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_MOVIE_ID),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class MovieImageEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,
    @ColumnInfo(name = COLUMN_PATH)
    val path: String,
    @ColumnInfo(name = COLUMN_WIDTH)
    val width: Int,
    @ColumnInfo(name = COLUMN_TYPE)
    val type: Type,
    @ColumnInfo(name = COLUMN_FK_MOVIE_ID, index = true)
    val movieId: Long,
) {
    enum class Type(val value: Int) {
        POSTER(0),
        BACKDROP(1),
    }
}
