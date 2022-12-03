package com.louisfn.somovie.test.testfixtures.android.data.response

import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.test.shared.kotlin.FakeFactory

object FakeMovieResponseFactory {

    fun create(nbr: Int): List<MovieResponse> = FakeFactory.create(nbr)
}
