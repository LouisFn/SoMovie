package com.louisfn.somovie.test.testfixtures.android.data.database

import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.test.shared.kotlin.FakeFactory

object FakeMovieEntityFactory {

    fun create(nbr: Int): List<MovieEntity> = FakeFactory.create(nbr)
}
