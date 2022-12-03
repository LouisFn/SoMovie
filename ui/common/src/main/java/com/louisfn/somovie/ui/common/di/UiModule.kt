package com.louisfn.somovie.ui.common.di

import com.louisfn.somovie.ui.common.error.DefaultErrorsDispatcher
import com.louisfn.somovie.ui.common.error.ErrorsDispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface UiModule {

    @Binds
    @Singleton
    fun provideErrorsDispatcher(errorsDispatcher: DefaultErrorsDispatcher): ErrorsDispatcher
}
