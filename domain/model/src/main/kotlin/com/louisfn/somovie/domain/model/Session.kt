package com.louisfn.somovie.domain.model

data class Session(
    val sessionId: String?,
    val languageIso639: String?,
    val account: Account?
)
