package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.*

@Entity(
    tableName = TABLE_ACTOR,
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_MOVIE_ID),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_PERSON_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ActorEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,
    @ColumnInfo(name = COLUMN_CHARACTER)
    val character: String,
    @ColumnInfo(name = COLUMN_ORDER)
    val order: Int,
    @ColumnInfo(name = COLUMN_FK_MOVIE_ID, index = true)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_FK_PERSON_ID, index = true)
    val personId: Long
)
