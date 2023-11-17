package com.louisfn.somovie.data.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient

internal object OkHttpClientFactory {

    fun create(
        applicationInterceptors: Set<Interceptor>,
        networkInterceptors: Set<Interceptor>,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .apply {
                applicationInterceptors.forEach { addInterceptor(it) }
                networkInterceptors.forEach { addNetworkInterceptor(it) }
            }
            .build()
}
