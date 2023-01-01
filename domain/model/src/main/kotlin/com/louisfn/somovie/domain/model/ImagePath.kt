package com.louisfn.somovie.domain.model

interface ImagePath {
    val value: String
}

@JvmInline
value class PosterPath(override val value: String) : ImagePath

@JvmInline
value class LogoPath(override val value: String) : ImagePath

@JvmInline
value class BackdropPath(override val value: String) : ImagePath

@JvmInline
value class ProfilePath(override val value: String) : ImagePath
