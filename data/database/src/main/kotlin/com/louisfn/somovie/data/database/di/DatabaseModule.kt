package com.louisfn.somovie.data.database.di

import android.app.Application
import androidx.room.Room
import com.louisfn.somovie.core.common.annotation.IoDispatcher
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.DATABASE_NAME
import com.louisfn.somovie.data.database.DatabaseHelper
import com.louisfn.somovie.data.database.DefaultDatabaseHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AppDatabase =
        Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .setQueryExecutor(ioDispatcher.asExecutor())
            .fallbackToDestructiveMigration()
            .build()
}

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseBindsModule {

    @Binds
    fun provideDatabaseHelper(defaultDatabaseHelper: DefaultDatabaseHelper): DatabaseHelper
}
