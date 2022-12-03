package com.louisfn.somovie.data.datastore.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SessionData(
    @Json(name = "session_id")
    val sessionId: String? = null,
    @Json(name = "language")
    val languageIso639: String? = null,
    @Json(name = "account")
    val account: Account? = null
) : DataStoreData {

    @JsonClass(generateAdapter = true)
    data class Account(
        @Json(name = "id")
        val id: Long,
        @Json(name = "name")
        val name: String,
        @Json(name = "username")
        val username: String
    )
}
