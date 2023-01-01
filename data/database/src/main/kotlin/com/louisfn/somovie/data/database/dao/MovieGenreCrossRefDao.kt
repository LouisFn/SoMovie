package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.TABLE_MOVIE_GENRE
import com.louisfn.somovie.data.database.entity.MovieGenreCrossRefEntity

@Dao
internal abstract class MovieGenreCrossRefDao : BaseDao<MovieGenreCrossRefEntity>(TABLE_MOVIE_GENRE) {

    @Query("DELETE FROM $TABLE_MOVIE_GENRE WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract suspend fun delete(movieId: Long)
}
