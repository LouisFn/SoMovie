package com.louisfn.somovie.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.louisfn.somovie.data.database.*
import com.louisfn.somovie.data.database.entity.ExploreEntity
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.database.relation.MovieWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class MovieDao : BaseDao<MovieEntity>(TABLE_MOVIE) {

    @Query(
        "SELECT m.* FROM $TABLE_EXPLORE as d " +
            "INNER JOIN $TABLE_MOVIE as m ON d.$COLUMN_FK_MOVIE_ID == m.$COLUMN_ID " +
            "WHERE d.$COLUMN_CATEGORY = :category " +
            "ORDER BY d.$COLUMN_ID " +
            "LIMIT :limit "
    )
    abstract fun changes(category: ExploreEntity.Category, limit: Int): Flow<List<MovieEntity>>

    @Query(
        "SELECT m.* FROM $TABLE_EXPLORE as d " +
            "INNER JOIN $TABLE_MOVIE as m ON d.$COLUMN_FK_MOVIE_ID == m.$COLUMN_ID " +
            "WHERE d.$COLUMN_CATEGORY = :category " +
            "ORDER BY d.$COLUMN_ID"
    )
    abstract fun getPaging(category: ExploreEntity.Category): PagingSource<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM $TABLE_MOVIE WHERE $COLUMN_ID  = :movieId")
    abstract fun movieWithRelationChanges(movieId: Long): Flow<MovieWithRelations>

    @Query("UPDATE $TABLE_MOVIE SET $COLUMN_WATCHLIST = :watchlist WHERE $COLUMN_ID = :movieId")
    abstract fun updateWatchlist(movieId: Long, watchlist: Boolean)
}
