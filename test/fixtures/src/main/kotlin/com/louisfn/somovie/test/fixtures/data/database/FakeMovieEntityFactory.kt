package com.louisfn.somovie.test.fixtures.data.database

import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.test.shared.FakeFactory

object FakeMovieEntityFactory {

    fun create(nbr: Int): List<MovieEntity> = FakeFactory.create(nbr)
}
