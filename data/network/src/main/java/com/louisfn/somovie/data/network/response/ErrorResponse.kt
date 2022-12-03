package com.louisfn.somovie.data.network.response

import com.louisfn.somovie.data.network.StatusCode
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "status_code")
    val statusCode: StatusCode,
    @Json(name = "status_message")
    val statusMessage: String
)
