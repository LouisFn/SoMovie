package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.TABLE_YOUTUBE_VIDEO
import com.louisfn.somovie.data.database.entity.YoutubeVideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class YoutubeVideoDao : BaseDao<YoutubeVideoEntity>(TABLE_YOUTUBE_VIDEO) {

    @Query("SELECT * FROM $TABLE_YOUTUBE_VIDEO WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract fun changes(movieId: Long): Flow<List<YoutubeVideoEntity>>

    @Query("DELETE FROM $TABLE_YOUTUBE_VIDEO WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract suspend fun delete(movieId: Long)
}
