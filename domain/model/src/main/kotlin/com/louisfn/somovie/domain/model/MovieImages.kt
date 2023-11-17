package com.louisfn.somovie.domain.model

data class MovieImages(
    val backdrops: List<Image<BackdropPath>>,
    val posters: List<Image<PosterPath>>,
) {

    data class Image<T : ImagePath>(
        val path: T,
        val width: Int,
    )
}
