package com.louisfn.somovie.domain.repository

import androidx.annotation.AnyThread
import com.louisfn.somovie.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    @AnyThread
    fun sessionChanges(): Flow<Session>

    @AnyThread
    suspend fun getSession(): Session

    @AnyThread
    suspend fun updateSession(transform: suspend (t: Session) -> Session)
}
