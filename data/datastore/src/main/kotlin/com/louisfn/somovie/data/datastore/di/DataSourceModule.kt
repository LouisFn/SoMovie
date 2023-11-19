package com.louisfn.somovie.data.datastore.di

import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.datasource.DefaultDataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.data.datastore.model.TmdbConfigurationData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    fun provideTmdbConfigurationLocalDataSource(
        defaultTmdbConfigurationLocalDataSource: DefaultDataStoreLocalDataSource<TmdbConfigurationData>,
    ): DataStoreLocalDataSource<TmdbConfigurationData>

    @Binds
    fun provideSessionLocalDataSource(
        defaultSessionLocalDataSource: DefaultDataStoreLocalDataSource<SessionData>,
    ): DataStoreLocalDataSource<SessionData>
}
