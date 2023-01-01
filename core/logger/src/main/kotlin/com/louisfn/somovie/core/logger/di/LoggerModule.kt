package com.louisfn.somovie.core.logger.di

import com.louisfn.somovie.core.logger.LogAdapter
import com.louisfn.somovie.core.logger.TimberLogAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface LoggerModule {

    @Binds
    @Singleton
    fun provideLogAdapter(logAdapter: TimberLogAdapter): LogAdapter
}
