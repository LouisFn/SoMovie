package com.louisfn.somovie.data.network.di

import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.data.network.ApiService
import com.louisfn.somovie.data.network.ApiServiceFactory
import com.louisfn.somovie.data.network.BuildConfig
import com.louisfn.somovie.data.network.OkHttpClientFactory
import com.louisfn.somovie.data.network.adapter.MovieVideoSiteAdapter
import com.louisfn.somovie.data.network.adapter.MovieVideoTypeAdapter
import com.louisfn.somovie.data.network.adapter.StatusCodeAdapter
import com.louisfn.somovie.data.network.interceptor.AuthInterceptor
import com.louisfn.somovie.data.network.interceptor.LanguageInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    @MoshiApiService
    fun provideMoshi(moshi: Moshi): Moshi =
        moshi
            .newBuilder()
            .add(MovieVideoTypeAdapter)
            .add(MovieVideoSiteAdapter)
            .add(StatusCodeAdapter)
            .build()

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient,
        @MoshiApiService moshi: Moshi,
    ): ApiService =
        ApiServiceFactory.create(
            baseUrl = BuildConfig.API_BASE_URL,
            okHttpClient = okHttpClient,
            moshi = moshi,
        )

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationInterceptorOkHttpClient applicationInterceptors: Set<@JvmSuppressWildcards Interceptor>,
        @NetworkInterceptorOkHttpClient networkInterceptors: Set<@JvmSuppressWildcards Interceptor>,
    ): OkHttpClient = OkHttpClientFactory.create(
        applicationInterceptors = applicationInterceptors,
        networkInterceptors = networkInterceptors,
    )

    @ApplicationInterceptorOkHttpClient
    @IntoSet
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @ApplicationInterceptorOkHttpClient
    @IntoSet
    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionLocalDataSource: DataStoreLocalDataSource<SessionData>): Interceptor =
        AuthInterceptor(sessionLocalDataSource)

    @ApplicationInterceptorOkHttpClient
    @IntoSet
    @Provides
    @Singleton
    fun provideLanguageInterceptor(): Interceptor =
        LanguageInterceptor()
}
