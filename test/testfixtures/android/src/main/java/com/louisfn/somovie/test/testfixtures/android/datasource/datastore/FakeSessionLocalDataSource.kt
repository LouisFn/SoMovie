package com.louisfn.somovie.test.testfixtures.android.datasource.datastore

import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.SessionData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.updateAndGet

class FakeSessionLocalDataSource : DataStoreLocalDataSource<SessionData> {

    private val sessionData = MutableStateFlow(SessionData())

    override fun dataChanges(): Flow<SessionData> = sessionData

    override suspend fun getData(): SessionData = sessionData.value

    override suspend fun updateData(transform: suspend (t: SessionData) -> SessionData): SessionData =
        sessionData.updateAndGet { transform(sessionData.value) }

    fun setSession(sessionData: SessionData) {
        this.sessionData.value = sessionData
    }
}
