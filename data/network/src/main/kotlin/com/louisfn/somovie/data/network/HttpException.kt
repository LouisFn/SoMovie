package com.louisfn.somovie.data.network

internal class HttpException(
    val httpCode: Int,
    val statusCode: StatusCode?,
    val statusMessage: String?,
) : RuntimeException("HTTP $httpCode - Code: $statusCode - Message: $statusMessage")
