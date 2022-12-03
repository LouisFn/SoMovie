package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread

interface AppRepository {

    @AnyThread
    suspend fun clearDatabase()
}
