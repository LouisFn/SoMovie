package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.TABLE_MOVIE_PRODUCTION_COUNTRY
import com.louisfn.somovie.data.database.entity.MovieProductionCountryEntity

@Dao
internal abstract class ProductionCountryDao : BaseDao<MovieProductionCountryEntity>(TABLE_MOVIE_PRODUCTION_COUNTRY) {

    @Query("DELETE FROM $TABLE_MOVIE_PRODUCTION_COUNTRY WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract suspend fun delete(movieId: Long)
}
