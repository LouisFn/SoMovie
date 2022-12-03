package com.louisfn.somovie.app.di

import com.louisfn.somovie.app.util.TimberLogAdapter
import com.louisfn.somovie.common.logger.LogAdapter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ApplicationModule {

    @Binds
    @Singleton
    fun provideLogAdapter(logAdapter: TimberLogAdapter): LogAdapter
}
