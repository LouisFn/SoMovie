package com.louisfn.somovie.data.mapper

import androidx.annotation.AnyThread
import com.louisfn.somovie.core.common.provider.DateTimeProvider
import com.louisfn.somovie.data.database.entity.CompanyEntity
import com.louisfn.somovie.data.database.entity.GenreEntity
import com.louisfn.somovie.data.database.entity.MovieEntity
import com.louisfn.somovie.data.database.entity.MovieProductionCountryEntity
import com.louisfn.somovie.data.database.relation.MovieWithRelations
import com.louisfn.somovie.data.network.response.MovieAccountStateResponse
import com.louisfn.somovie.data.network.response.MovieDetailsResponse
import com.louisfn.somovie.data.network.response.MovieGenreResponse
import com.louisfn.somovie.data.network.response.MovieResponse
import com.louisfn.somovie.domain.model.BackdropPath
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.domain.model.PosterPath
import javax.inject.Inject

class MovieMapper @Inject constructor(
    private val genreMapper: GenreMapper,
    private val countryMapper: CountryMapper,
    private val companyMapper: CompanyMapper,
    private val dateTimeProvider: DateTimeProvider,
) {

    //region Map entity to domain

    fun mapToDomain(entities: List<MovieEntity>): List<Movie> = entities.map(::mapToDomain)

    fun mapToDomain(entity: MovieEntity): Movie =
        mapToDomain(
            movie = entity,
            countries = emptyList(),
            companies = emptyList(),
            genres = emptyList(),
        )

    fun mapToDomain(entity: MovieWithRelations): Movie =
        mapToDomain(
            movie = entity.movie,
            countries = entity.productionCountries,
            companies = entity.productionCompanies,
            genres = entity.genres,
        )

    private fun mapToDomain(
        movie: MovieEntity,
        countries: List<MovieProductionCountryEntity>,
        companies: List<CompanyEntity>,
        genres: List<GenreEntity>,
    ) = Movie(
        id = movie.id,
        title = movie.title,
        originalTitle = movie.originalTitle,
        originalLanguage = movie.originalLanguage,
        overview = movie.overview,
        releaseDate = movie.releaseDate,
        voteAverage = movie.voteAverage,
        posterPath = movie.posterPath?.let(::PosterPath),
        backdropPath = movie.backdropPath?.let(::BackdropPath),
        watchlist = movie.watchlist,
        details = movie.details?.let { details ->
            Movie.Details(
                tagline = details.tagline,
                runtime = details.runtime,
                popularity = details.popularity,
                budget = details.budget,
                revenue = details.revenue,
                voteCount = details.voteCount,
                productionCountries = countryMapper.mapToDomain(countries),
                productionCompanies = companyMapper.mapToDomain(companies),
                genres = genreMapper.mapToDomain(genres),
            )
        },
    )

    //endregion

    //region Map response to entity

    fun mapToEntity(responses: List<MovieResponse>, fromWatchlist: Boolean): List<MovieEntity> =
        responses.map { mapToEntity(it, fromWatchlist) }

    fun mapToEntity(
        detailsResponse: MovieDetailsResponse,
        accountStateResponse: MovieAccountStateResponse?,
    ): MovieWithRelations =
        MovieWithRelations(
            movie = MovieEntity(
                id = detailsResponse.id,
                title = detailsResponse.title,
                originalTitle = detailsResponse.originalTitle,
                originalLanguage = detailsResponse.originalLanguage,
                overview = detailsResponse.overview,
                releaseDate = detailsResponse.releaseDate,
                posterPath = detailsResponse.posterPath,
                backdropPath = detailsResponse.backdropPath,
                voteAverage = detailsResponse.voteAverage,
                watchlist = accountStateResponse?.watchlist,
                updatedAt = dateTimeProvider.now(),
                detailsUpdatedAt = dateTimeProvider.now(),
                details = MovieEntity.Details(
                    tagline = detailsResponse.tagline,
                    runtime = detailsResponse.runtime,
                    popularity = detailsResponse.popularity,
                    revenue = detailsResponse.revenue,
                    budget = detailsResponse.budget,
                    voteCount = detailsResponse.voteCount,
                ),
            ),
            genres = detailsResponse.genres.map(::mapToEntity),
            productionCompanies = companyMapper.mapToEntity(detailsResponse.productionCompanies),
            productionCountries = countryMapper.mapToEntity(
                detailsResponse.id,
                detailsResponse.productionCountries,
            ),
        )

    @AnyThread
    private fun mapToEntity(response: MovieResponse, fromWatchlist: Boolean) = MovieEntity(
        id = response.id,
        title = response.title,
        originalTitle = response.originalTitle,
        originalLanguage = response.originalLanguage,
        overview = response.overview,
        releaseDate = response.releaseDate,
        posterPath = response.posterPath,
        backdropPath = response.backdropPath,
        voteAverage = response.voteAverage,
        details = null,
        watchlist = true.takeIf { fromWatchlist },
        updatedAt = dateTimeProvider.now(),
        detailsUpdatedAt = null,
    )

    private fun mapToEntity(response: MovieGenreResponse) = GenreEntity(
        id = response.id,
        name = response.name,
    )

    //endregion

    //region Map response to domain

    @JvmName("mapToResponseToDomain")
    fun mapToDomain(responses: List<MovieResponse>): List<Movie> =
        responses.map(::mapToDomain)

    @AnyThread
    private fun mapToDomain(response: MovieResponse) = Movie(
        id = response.id,
        title = response.title,
        originalTitle = response.originalTitle,
        originalLanguage = response.originalLanguage,
        overview = response.overview,
        releaseDate = response.releaseDate,
        posterPath = response.posterPath?.let(::PosterPath),
        backdropPath = response.posterPath?.let(::BackdropPath),
        voteAverage = response.voteAverage,
        watchlist = null,
        details = null,
    )
}
