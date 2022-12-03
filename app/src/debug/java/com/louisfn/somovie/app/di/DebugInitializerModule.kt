package com.louisfn.somovie.app.di

import com.louisfn.somovie.app.initializer.AppInitializer
import com.louisfn.somovie.app.initializer.FlipperInitializer
import com.louisfn.somovie.app.initializer.StrictModeInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DebugInitializerModule {

    @Binds
    @IntoSet
    internal abstract fun provideFlipperInitializer(flipperInitializer: FlipperInitializer): AppInitializer

    @Binds
    @IntoSet
    internal abstract fun provideStrictModeInitializer(strictModeInitializer: StrictModeInitializer): AppInitializer
}
