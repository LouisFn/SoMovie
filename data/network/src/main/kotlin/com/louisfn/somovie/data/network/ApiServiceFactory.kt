package com.louisfn.somovie.data.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal object ApiServiceFactory {

    fun create(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ApiService =
        setupService(
            baseUrl = baseUrl,
            okHttpClient = okHttpClient,
            moshi = moshi
        )

    private fun setupService(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
