package com.louisfn.somovie.data.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.louisfn.somovie.data.database.COLUMN_FK_COMPANY_ID
import com.louisfn.somovie.data.database.COLUMN_FK_GENRE_ID
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.entity.*

data class MovieWithRelations(
    @Embedded
    val movie: MovieEntity,

    @Relation(
        parentColumn = COLUMN_ID,
        entityColumn = COLUMN_ID,
        associateBy = Junction(
            value = MovieGenreCrossRefEntity::class,
            parentColumn = COLUMN_FK_MOVIE_ID,
            entityColumn = COLUMN_FK_GENRE_ID,
        ),
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = COLUMN_ID,
        entityColumn = COLUMN_ID,
        associateBy = Junction(
            value = MovieProductionCompanyCrossRefEntity::class,
            parentColumn = COLUMN_FK_MOVIE_ID,
            entityColumn = COLUMN_FK_COMPANY_ID,
        ),
    )
    val productionCompanies: List<CompanyEntity>,

    @Relation(
        parentColumn = COLUMN_ID,
        entityColumn = COLUMN_FK_MOVIE_ID,
    )
    val productionCountries: List<MovieProductionCountryEntity>,
)
