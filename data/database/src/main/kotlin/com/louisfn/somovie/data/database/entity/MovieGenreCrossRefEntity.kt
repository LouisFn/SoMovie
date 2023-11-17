package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.louisfn.somovie.data.database.COLUMN_FK_GENRE_ID
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.TABLE_MOVIE_GENRE

@Entity(
    tableName = TABLE_MOVIE_GENRE,
    primaryKeys = [COLUMN_FK_MOVIE_ID, COLUMN_FK_GENRE_ID],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_MOVIE_ID),
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_GENRE_ID),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class MovieGenreCrossRefEntity(
    @ColumnInfo(name = COLUMN_FK_MOVIE_ID, index = true)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_FK_GENRE_ID, index = true)
    val genreId: Long,
)
