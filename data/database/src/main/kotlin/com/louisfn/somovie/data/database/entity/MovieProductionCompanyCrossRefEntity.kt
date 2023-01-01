package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.louisfn.somovie.data.database.COLUMN_FK_COMPANY_ID
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.TABLE_MOVIE_PRODUCTION_COMPANY

@Entity(
    tableName = TABLE_MOVIE_PRODUCTION_COMPANY,
    primaryKeys = [COLUMN_FK_MOVIE_ID, COLUMN_FK_COMPANY_ID],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_MOVIE_ID),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CompanyEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_COMPANY_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieProductionCompanyCrossRefEntity(
    @ColumnInfo(name = COLUMN_FK_MOVIE_ID, index = true)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_FK_COMPANY_ID, index = true)
    val companyId: Long
)
