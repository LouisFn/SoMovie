package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.TABLE_MOVIE_IMAGE
import com.louisfn.somovie.data.database.entity.MovieImageEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class MovieImageDao : BaseDao<MovieImageEntity>(TABLE_MOVIE_IMAGE) {

    @Query("SELECT * FROM $TABLE_MOVIE_IMAGE WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract fun changes(movieId: Long): Flow<List<MovieImageEntity>>

    @Query("DELETE FROM $TABLE_MOVIE_IMAGE WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract suspend fun delete(movieId: Long)
}
