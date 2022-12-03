package com.louisfn.somovie.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewSessionResponse(
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "session_id")
    val sessionId: String
)
