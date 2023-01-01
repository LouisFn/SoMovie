package com.louisfn.somovie.app.di

import android.app.Application
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.louisfn.somovie.data.network.di.NetworkInterceptorOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class FlipperModule {

    @Provides
    @Singleton
    fun provideInspectorFlipperPlugin(application: Application) =
        InspectorFlipperPlugin(application, DescriptorMapping.withDefaults())

    @Provides
    @Singleton
    fun provideNetworkFlipperPlugin() = NetworkFlipperPlugin()

    @Provides
    fun provideCrashReporterPlugin(): CrashReporterPlugin =
        CrashReporterPlugin.getInstance()

    @Provides
    @IntoSet
    @NetworkInterceptorOkHttpClient
    fun provideFlipperOkhttpInterceptor(networkFlipperPlugin: NetworkFlipperPlugin): Interceptor =
        FlipperOkhttpInterceptor(networkFlipperPlugin)
}
