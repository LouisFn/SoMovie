package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread

interface AuthenticationRepository {

    @AnyThread
    suspend fun getRequestToken(): String

    @AnyThread
    suspend fun createNewSession(requestToken: String): String
}
