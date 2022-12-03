package com.louisfn.somovie.feature.login.di

import com.louisfn.somovie.feature.login.DefaultLogInManager
import com.louisfn.somovie.feature.login.LogInManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface LogInModule {

    @Binds
    fun provideLogInManager(logInManager: DefaultLogInManager): LogInManager
}
