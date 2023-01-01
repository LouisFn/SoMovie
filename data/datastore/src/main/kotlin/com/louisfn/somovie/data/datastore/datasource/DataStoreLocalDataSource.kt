package com.louisfn.somovie.data.datastore.datasource

import androidx.annotation.AnyThread
import androidx.datastore.core.DataStore
import com.louisfn.somovie.core.common.annotation.IoDispatcher
import com.louisfn.somovie.data.datastore.model.DataStoreData
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.invoke
import javax.inject.Inject

interface DataStoreLocalDataSource<T : DataStoreData> {

    @AnyThread
    fun dataChanges(): Flow<T>

    @AnyThread
    suspend fun getData(): T

    @AnyThread
    suspend fun updateData(transform: suspend (t: T) -> T): T
}

internal class DefaultDataStoreLocalDataSource<T : DataStoreData> @Inject constructor(
    private val lazyDataStore: Lazy<DataStore<T>>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : DataStoreLocalDataSource<T> {

    private val dataStore
        get() = lazyDataStore.get()

    override fun dataChanges(): Flow<T> =
        dataStore.data
            .flowOn(ioDispatcher)

    override suspend fun getData(): T = ioDispatcher {
        dataStore.data.first()
    }

    override suspend fun updateData(transform: suspend (t: T) -> T) = ioDispatcher {
        dataStore.updateData(transform)
    }
}
