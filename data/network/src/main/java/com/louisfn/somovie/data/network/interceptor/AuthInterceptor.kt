package com.louisfn.somovie.data.network.interceptor

import com.louisfn.somovie.data.datastore.datasource.DataStoreLocalDataSource
import com.louisfn.somovie.data.datastore.model.SessionData
import com.louisfn.somovie.data.network.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(private val sessionLocalDataSource: DataStoreLocalDataSource<SessionData>) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val httpUrlBuilder =
            request.url
                .newBuilder()
                .addQueryParameter(PARAM_API_KEY, BuildConfig.API_KEY)

        runBlocking { sessionLocalDataSource.getData().sessionId }?.let {
            httpUrlBuilder.addQueryParameter(PARAM_SESSION_ID, it)
        }

        val newRequest = request.newBuilder().url(httpUrlBuilder.build()).build()
        return chain.proceed(newRequest)
    }

    companion object {
        const val PARAM_API_KEY = "api_key"
        const val PARAM_SESSION_ID = "session_id"
    }
}
