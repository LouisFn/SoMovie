package com.louisfn.somovie.data.database.dao

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

abstract class BaseDao<T : Any>(val tableName: String) {

    //region Get

    suspend fun getAll(): List<T> = getAll(SimpleSQLiteQuery("SELECT * FROM $tableName"))

    @RawQuery
    protected abstract suspend fun getAll(query: SupportSQLiteQuery): List<T>

    //endregion

    //region Insert

    @Insert
    abstract suspend fun insertOrAbort(entity: T): Long

    @Insert
    abstract suspend fun insertOrAbort(entities: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrReplace(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertOrReplace(entities: List<T>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(entity: T): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertOrIgnore(entities: List<T>)

    @Transaction
    open suspend fun insertOrUpdate(entity: T) {
        if (insertOrIgnore(entity) == -1L) {
            update(entity)
        }
    }

    //endregion

    //region Update

    @Update
    abstract suspend fun update(entity: T)

    //endregion

    //region Delete

    @Delete
    abstract suspend fun delete(entity: T)

    //endregion
}
