package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.TABLE_MOVIE_PRODUCTION_COMPANY
import com.louisfn.somovie.data.database.entity.MovieProductionCompanyCrossRefEntity

@Dao
internal abstract class MovieProductionCompanyCrossRefDao :
    BaseDao<MovieProductionCompanyCrossRefEntity>(TABLE_MOVIE_PRODUCTION_COMPANY) {

    @Query("DELETE FROM $TABLE_MOVIE_PRODUCTION_COMPANY WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract suspend fun delete(movieId: Long)
}
