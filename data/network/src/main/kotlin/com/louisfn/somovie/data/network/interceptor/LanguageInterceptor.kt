package com.louisfn.somovie.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

internal class LanguageInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrl =
            request.url
                .newBuilder()
                .addQueryParameter(PARAM_LANGUAGE, Locale.getDefault().language)
                .build()

        val newRequest = request.newBuilder().url(httpUrl).build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val PARAM_LANGUAGE = "language"
    }
}
