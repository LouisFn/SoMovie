package com.louisfn.somovie.test.testfixtures.android.data.domain

import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.test.shared.kotlin.FakeFactory

object FakeMovieFactory {

    fun create(nbr: Int): List<Movie> = FakeFactory.create(nbr)

    fun create(): Movie = FakeFactory.create<Movie>(1).first()

    fun createEmpty() = Movie(
        id = 0,
        title = "",
        originalTitle = "",
        originalLanguage = "",
        overview = "",
        releaseDate = null,
        voteAverage = 0f,
        posterPath = null,
        backdropPath = null,
        watchlist = null,
        details = null
    )
}
