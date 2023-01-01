package com.louisfn.somovie.data.database

import androidx.annotation.AnyThread
import androidx.room.withTransaction
import com.louisfn.somovie.core.common.annotation.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface DatabaseHelper {
    @AnyThread
    suspend fun <R> withTransaction(block: suspend () -> R): R

    @AnyThread
    suspend fun clearAllTables()
}

internal class DefaultDatabaseHelper @Inject constructor(
    private val appDatabase: AppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DatabaseHelper {

    override suspend fun <R> withTransaction(block: suspend () -> R): R = ioDispatcher {
        appDatabase.withTransaction(block)
    }

    override suspend fun clearAllTables() = ioDispatcher {
        appDatabase.clearAllTables()
    }
}
