package com.louisfn.somovie.data.repository.di

import com.louisfn.somovie.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun provideAppRepository(defaultAppRepository: DefaultAppRepository): AppRepository

    @Binds
    fun provideMoviesRepository(defaultMovieRepository: DefaultMovieRepository): MovieRepository

    @Binds
    fun provideMovieImagesRepository(movieImagesRepositoryImpl: DefaultMovieImageRepository): MovieImageRepository

    @Binds
    fun provideMovieCreditsRepository(defaultMovieCreditsRepository: DefaultMovieCreditsRepository): MovieCreditsRepository

    @Binds
    fun provideAuthenticationRepository(defaultAuthenticationRepository: DefaultAuthenticationRepository): AuthenticationRepository

    @Binds
    fun provideSessionRepository(defaultSessionRepository: DefaultSessionRepository): SessionRepository

    @Binds
    fun provideMovieVideoRepository(defaultMovieVideoRepository: DefaultMovieVideoRepository): MovieVideoRepository

    @Binds
    fun provideTmdbConfigurationRepository(defaultTmdbConfigurationRepository: DefaultTmdbConfigurationRepository): TmdbConfigurationRepository

    @Binds
    fun provideMovieDiscoverRepository(defaultMovieDiscoverRepository: DefaultMovieDiscoverRepository): MovieDiscoverRepository

    @Binds
    fun provideAccountRepository(defaultAccountRepository: DefaultAccountRepository): AccountRepository

    @Binds
    fun provideWatchlistRepository(defaultWatchlistRepository: DefaultWatchlistRepository): WatchlistRepository
}
