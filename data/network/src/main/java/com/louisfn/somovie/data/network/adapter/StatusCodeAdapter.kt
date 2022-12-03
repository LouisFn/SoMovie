package com.louisfn.somovie.data.network.adapter

import com.louisfn.somovie.data.network.StatusCode
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal object StatusCodeAdapter {

    @FromJson
    fun fromJson(code: Int): StatusCode =
        StatusCode.fromCode(code)

    @ToJson
    fun toJson(statusCode: StatusCode): Int =
        statusCode.code
}
