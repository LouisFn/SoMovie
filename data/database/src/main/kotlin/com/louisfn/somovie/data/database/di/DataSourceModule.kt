package com.louisfn.somovie.data.database.di

import com.louisfn.somovie.data.database.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    fun provideRemoteKeyLocalDataSource(defaultRemoteKeyLocalDataSource: DefaultRemoteKeyLocalDataSource): RemoteKeyLocalDataSource

    @Binds
    fun provideMovieLocalDataSource(defaultMovieLocalDataSource: DefaultMovieLocalDataSource): MovieLocalDataSource

    @Binds
    fun provideMovieImagesImagesLocalDataSource(defaultMovieImagesLocalDataSource: DefaultMovieImageLocalDataSource): MovieImageLocalDataSource

    @Binds
    fun provideMovieCreditsLocalDataSource(defaultMovieCreditsLocalDataSource: DefaultMovieCreditsLocalDataSource): MovieCreditsLocalDataSource

    @Binds
    fun provideMovieVideosLocalDataSource(defaultMovieVideosLocalDataSource: DefaultMovieVideoLocalDataSource): MovieVideoLocalDataSource

    @Binds
    fun provideWatchlistLocalDataSource(defaultWatchlistLocalDataSource: DefaultWatchlistLocalDataSource): WatchlistLocalDataSource
}
