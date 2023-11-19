package com.louisfn.somovie.data.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
enum class StatusCode(val code: Int) {
    AUTHENTICATION_FAILED(3),
    SESSION_DENIED(17),
    RESOURCE_REQUESTED_NOT_FOUND(34),
    UNKNOWN(-1),
    ;

    companion object {
        fun fromCode(code: Int): StatusCode = values().firstOrNull { it.code == code } ?: UNKNOWN
    }
}
