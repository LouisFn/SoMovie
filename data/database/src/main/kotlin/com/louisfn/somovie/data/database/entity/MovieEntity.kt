package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.*
import java.time.Duration
import java.time.Instant
import java.time.LocalDate

@Entity(tableName = TABLE_MOVIE)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,
    @ColumnInfo(name = COLUMN_ORIGINAL_TITLE)
    val originalTitle: String,
    @ColumnInfo(name = COLUMN_ORIGINAL_LANGUAGE)
    val originalLanguage: String,
    @ColumnInfo(name = COLUMN_OVERVIEW)
    val overview: String,
    @ColumnInfo(name = COLUMN_RELEASE_DATE)
    val releaseDate: LocalDate?,
    @ColumnInfo(name = COLUMN_VOTE_AVERAGE)
    val voteAverage: Float,
    @ColumnInfo(name = COLUMN_POSTER_PATH)
    val posterPath: String?,
    @ColumnInfo(name = COLUMN_BACKDROP_PATH)
    val backdropPath: String?,
    @ColumnInfo(name = COLUMN_WATCHLIST)
    val watchlist: Boolean?,
    @ColumnInfo(name = COLUMN_UPDATED_AT)
    val updatedAt: Instant?,
    @ColumnInfo(name = COLUMN_DETAILS_UPDATED_AT)
    val detailsUpdatedAt: Instant?,
    @Embedded
    val details: Details?
) {
    data class Details(
        @ColumnInfo(name = COLUMN_TAGLINE)
        val tagline: String?,
        @ColumnInfo(name = COLUMN_RUNTIME)
        val runtime: Duration?,
        @ColumnInfo(name = COLUMN_POPULARITY)
        val popularity: Float,
        @ColumnInfo(name = COLUMN_BUDGET)
        val budget: Int,
        @ColumnInfo(name = COLUMN_REVENUE)
        val revenue: Int,
        @ColumnInfo(name = COLUMN_VOTE_COUNT)
        val voteCount: Int
    )
}
