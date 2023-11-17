package com.louisfn.somovie.data.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.database.DatabaseHelper
import javax.inject.Inject

interface AppRepository {

    @AnyThread
    suspend fun clearDatabase()
}

internal class DefaultAppRepository @Inject constructor(
    private val databaseHelper: DatabaseHelper,
) : AppRepository {

    override suspend fun clearDatabase() {
        databaseHelper.clearAllTables()
    }
}
