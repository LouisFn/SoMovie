package com.louisfn.somovie.data.network.body

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestTokenBody(
    @Json(name = "request_token")
    val requestToken: String
)
