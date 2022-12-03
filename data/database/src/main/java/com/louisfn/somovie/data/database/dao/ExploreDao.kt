package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_CATEGORY
import com.louisfn.somovie.data.database.COLUMN_PAGE
import com.louisfn.somovie.data.database.TABLE_EXPLORE
import com.louisfn.somovie.data.database.entity.ExploreEntity

@Dao
internal abstract class ExploreDao : BaseDao<ExploreEntity>(TABLE_EXPLORE) {

    @Query("DELETE FROM $TABLE_EXPLORE WHERE $COLUMN_CATEGORY = :category")
    abstract suspend fun delete(category: ExploreEntity.Category)

    @Query("DELETE FROM $TABLE_EXPLORE WHERE $COLUMN_CATEGORY = :category AND $COLUMN_PAGE = :page")
    abstract suspend fun delete(category: ExploreEntity.Category, page: Int)
}
