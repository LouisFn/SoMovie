package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestTokenResponse(
    @Json(name = "request_token")
    val requestToken: String
)
