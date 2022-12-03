package com.louisfn.somovie.data.database.entity

import androidx.room.*
import com.louisfn.somovie.data.database.*

@Entity(
    tableName = TABLE_EXPLORE,
    indices = [
        Index(COLUMN_CATEGORY, COLUMN_FK_MOVIE_ID, unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_MOVIE_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExploreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long = 0,
    @ColumnInfo(name = COLUMN_FK_MOVIE_ID, index = true)
    val movieId: Long,
    @ColumnInfo(name = COLUMN_CATEGORY)
    val category: Category,
    @ColumnInfo(name = COLUMN_PAGE)
    val page: Int
) {
    enum class Category(val value: Int) {
        POPULAR(0),
        NOW_PLAYING(1),
        TOP_RATED(2),
        UPCOMING(3)
    }
}
