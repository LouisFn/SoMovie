package com.louisfn.somovie.data.repository

import com.louisfn.somovie.data.database.DatabaseHelper
import com.louisfn.somovie.domain.repository.AppRepository
import javax.inject.Inject

internal class DefaultAppRepository @Inject constructor(
    private val databaseHelper: DatabaseHelper
) : AppRepository {

    override suspend fun clearDatabase() {
        databaseHelper.clearAllTables()
    }
}
