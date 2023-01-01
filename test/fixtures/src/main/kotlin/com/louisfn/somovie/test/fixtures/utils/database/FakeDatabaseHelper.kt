package com.louisfn.somovie.test.fixtures.utils.database

import com.louisfn.somovie.data.database.DatabaseHelper

class FakeDatabaseHelper : DatabaseHelper {

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        block()

    override suspend fun clearAllTables() {
    }
}
