package com.louisfn.somovie.common.di

import com.louisfn.somovie.common.moshi.adapter.DurationAdapter
import com.louisfn.somovie.common.moshi.adapter.InstantAdapter
import com.louisfn.somovie.common.moshi.adapter.LocalDateAdapter
import com.louisfn.somovie.common.moshi.adapter.OffsetDateTimeAdapter
import com.louisfn.somovie.common.provider.DateTimeProvider
import com.louisfn.somovie.common.provider.DefaultDateTimeProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class CommonModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .add(LocalDateAdapter)
            .add(DurationAdapter)
            .add(OffsetDateTimeAdapter)
            .add(InstantAdapter)
            .build()
}

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonModuleBinds {

    @Binds
    @Singleton
    fun provideTimeProvider(dateTimeProvider: DefaultDateTimeProvider): DateTimeProvider
}
