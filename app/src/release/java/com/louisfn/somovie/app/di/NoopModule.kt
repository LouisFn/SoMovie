package com.louisfn.somovie.app.di

import com.louisfn.somovie.data.network.di.NetworkInterceptorOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NoopModule {

    @NetworkInterceptorOkHttpClient
    @IntoSet
    @Provides
    @Singleton
    fun providesNoopInterceptor(): Interceptor = Interceptor { it.proceed(it.request()) }
}
