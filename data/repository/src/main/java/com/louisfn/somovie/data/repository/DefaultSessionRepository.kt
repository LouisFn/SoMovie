package com.louisfn.somovie.data.repository

import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.data.mapper.SessionMapper
import com.louisfn.somovie.domain.model.Session
import com.louisfn.somovie.domain.repository.SessionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.invoke
import javax.inject.Inject

internal class DefaultSessionRepository @Inject constructor(
    private val localDataSource: DataStoreLocalDataSource<SessionData>,
    private val mapper: SessionMapper,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : SessionRepository {

    override fun sessionChanges(): Flow<Session> =
        localDataSource.dataChanges()
            .map(mapper::mapToDomain)
            .flowOn(defaultDispatcher)

    override suspend fun getSession(): Session = defaultDispatcher {
        mapper.mapToDomain(localDataSource.getData())
    }

    override suspend fun updateSession(transform: suspend (t: Session) -> Session) {
        localDataSource.updateData {
            mapper.mapToData(transform(mapper.mapToDomain(it)))
        }
    }
}
