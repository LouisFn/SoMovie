package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.*
import java.time.OffsetDateTime

@Entity(
    tableName = TABLE_YOUTUBE_VIDEO,
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = arrayOf(COLUMN_ID),
            childColumns = arrayOf(COLUMN_FK_MOVIE_ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class YoutubeVideoEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,
    @ColumnInfo(name = COLUMN_KEY)
    val key: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_TYPE)
    val type: Type,
    @ColumnInfo(name = COLUMN_OFFICIAL)
    val official: Boolean,
    @ColumnInfo(name = COLUMN_PUBLISHED_AT)
    val publishedAt: OffsetDateTime,
    @ColumnInfo(name = COLUMN_FK_MOVIE_ID, index = true)
    val movieId: Long
) {
    enum class Type(val value: Int) {
        TRAILERS(0),
        TEASERS(1),
        CLIPS(2),
        BEHIND(3),
        BLOOPERS(4),
        FEATURETTES(5)
    }
}
