package com.louisfn.somovie.test.fixtures.data.response

import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.test.shared.FakeFactory

object FakeMovieResponseFactory {

    fun create(nbr: Int): List<MovieResponse> = FakeFactory.create(nbr)
}
