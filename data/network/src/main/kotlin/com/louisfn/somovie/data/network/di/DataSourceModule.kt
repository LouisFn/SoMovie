package com.louisfn.somovie.data.network.di

import com.louisfn.somovie.data.network.datasource.AccountRemoteDataSource
import com.louisfn.somovie.data.network.datasource.AuthenticationRemoteDataSource
import com.louisfn.somovie.data.network.datasource.ConfigurationRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultAccountRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultAuthenticationRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultConfigurationRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultMovieCreditsRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultMovieDiscoverRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultMovieImageRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultMovieRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultMovieVideoRemoteDataSource
import com.louisfn.somovie.data.network.datasource.DefaultWatchlistRemoteDataSource
import com.louisfn.somovie.data.network.datasource.MovieCreditsRemoteDataSource
import com.louisfn.somovie.data.network.datasource.MovieDiscoverRemoteDataSource
import com.louisfn.somovie.data.network.datasource.MovieImageRemoteDataSource
import com.louisfn.somovie.data.network.datasource.MovieRemoteDataSource
import com.louisfn.somovie.data.network.datasource.MovieVideoRemoteDataSource
import com.louisfn.somovie.data.network.datasource.WatchlistRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    fun provideMoviesRemoteDataSource(defaultMoviesRemoteDataSource: DefaultMovieRemoteDataSource): MovieRemoteDataSource

    @Binds
    fun provideMovieImagesRemoteDataSource(defaultMovieImagesRemoteDataSource: DefaultMovieImageRemoteDataSource): MovieImageRemoteDataSource

    @Binds
    fun provideMovieCreditsRemoteDataSource(defaultMovieCreditsRemoteDataSource: DefaultMovieCreditsRemoteDataSource): MovieCreditsRemoteDataSource

    @Binds
    fun provideAuthenticationRemoteDataSource(
        defaultAuthenticationRemoteDataSource: DefaultAuthenticationRemoteDataSource
    ): AuthenticationRemoteDataSource

    @Binds
    fun provideMovieVideoRemoteDataSource(defaultMovieVideoRemoteDataSource: DefaultMovieVideoRemoteDataSource): MovieVideoRemoteDataSource

    @Binds
    fun provideConfigurationRemoteDataSource(
        defaultConfigurationRemoteDataSource: DefaultConfigurationRemoteDataSource
    ): ConfigurationRemoteDataSource

    @Binds
    fun provideMovieDiscoverRemoteDataSource(
        defaultMovieDiscoverRemoteDataSource: DefaultMovieDiscoverRemoteDataSource
    ): MovieDiscoverRemoteDataSource

    @Binds
    fun provideAccountRemoteDataSource(defaultAccountRemoteDataSource: DefaultAccountRemoteDataSource): AccountRemoteDataSource

    @Binds
    fun provideWatchlistRemoteDataSource(defaultWatchlistRemoteDataSource: DefaultWatchlistRemoteDataSource): WatchlistRemoteDataSource
}
