package com.louisfn.somovie.app.di

import com.louisfn.somovie.app.initializer.AppInitializer
import com.louisfn.somovie.app.initializer.CoilInitializer
import com.louisfn.somovie.app.initializer.DataInitializer
import com.louisfn.somovie.app.initializer.LoggerInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
internal abstract class InitializerModule {

    @Binds
    @IntoSet
    internal abstract fun provideDataInitializer(dataInitializer: DataInitializer): AppInitializer

    @Binds
    @IntoSet
    internal abstract fun provideCoilInitializer(coilInitializer: CoilInitializer): AppInitializer

    @Binds
    @IntoSet
    internal abstract fun provideLoggerInitializer(loggerInitializer: LoggerInitializer): AppInitializer
}
