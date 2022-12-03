package com.louisfn.somovie.test.testfixtures.android.data.mapper

import com.louisfn.somovie.data.mapper.CompanyMapper
import com.louisfn.somovie.data.mapper.CountryMapper
import com.louisfn.somovie.data.mapper.GenreMapper
import com.louisfn.somovie.data.mapper.MovieMapper
import com.louisfn.somovie.test.testfixtures.android.util.FakeDateTimeProvider

object MapperFactory {

    fun createMovieMapper(dateTimeProvider: FakeDateTimeProvider = FakeDateTimeProvider()) =
        MovieMapper(GenreMapper(), CountryMapper(), CompanyMapper(), dateTimeProvider)
}
