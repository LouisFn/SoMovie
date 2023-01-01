package com.louisfn.somovie.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.TABLE_MOVIE
import com.louisfn.somovie.data.database.TABLE_WATCHLIST
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.database.entity.WatchlistEntity

@Dao
internal abstract class WatchlistDao : BaseDao<WatchlistEntity>(TABLE_WATCHLIST) {

    @Query(
        "SELECT * FROM $TABLE_WATCHLIST as w " +
            "INNER JOIN $TABLE_MOVIE as m ON w.$COLUMN_FK_MOVIE_ID == m.$COLUMN_ID " +
            "ORDER BY w.$COLUMN_ID"
    )
    abstract fun getPaging(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM $TABLE_WATCHLIST WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract suspend fun delete(movieId: Long)

    @Query("DELETE FROM $TABLE_WATCHLIST")
    abstract suspend fun deleteAll()
}
