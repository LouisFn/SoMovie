package com.louisfn.somovie.common.di

import com.louisfn.somovie.common.annotation.ApplicationScope
import com.louisfn.somovie.common.annotation.DefaultDispatcher
import com.louisfn.somovie.common.annotation.IoDispatcher
import com.louisfn.somovie.common.annotation.MainDispatcher
import com.louisfn.somovie.common.annotation.MainImmediateDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @ApplicationScope
    @Singleton
    @Provides
    fun provideApplicationScope(@MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher) =
        CoroutineScope(SupervisorJob() + mainImmediateDispatcher)
}
