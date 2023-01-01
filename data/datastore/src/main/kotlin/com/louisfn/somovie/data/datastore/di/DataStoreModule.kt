package com.louisfn.somovie.data.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.louisfn.somovie.core.common.annotation.IoDispatcher
import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.data.datastore.model.TmdbConfigurationData
import com.louisfn.somovie.data.datastore.serializer.SessionSerializer
import com.louisfn.somovie.data.datastore.serializer.TmdbConfigurationSerializer
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataStoreModule {

    @Provides
    @Singleton
    fun provideSessionDataStore(
        @ApplicationContext context: Context,
        moshi: Moshi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DataStore<SessionData> =
        DataStoreFactory.create(
            serializer = SessionSerializer(moshi),
            produceFile = { context.dataStoreFile(SESSION_FILE_NAME) },
            scope = CoroutineScope(ioDispatcher + SupervisorJob())
        )

    @Provides
    @Singleton
    fun provideTmdbConfigurationDataStore(
        @ApplicationContext context: Context,
        moshi: Moshi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): DataStore<TmdbConfigurationData> =
        DataStoreFactory.create(
            serializer = TmdbConfigurationSerializer(moshi),
            produceFile = { context.dataStoreFile(TMDB_CONFIGURATION_FILE_NAME) },
            scope = CoroutineScope(ioDispatcher + SupervisorJob())
        )

    companion object {
        private const val SESSION_FILE_NAME = "session.json"
        private const val TMDB_CONFIGURATION_FILE_NAME = "tmdb_configuration.json"
    }
}
