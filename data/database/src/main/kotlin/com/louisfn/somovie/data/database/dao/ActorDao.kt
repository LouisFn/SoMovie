package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.TABLE_ACTOR
import com.louisfn.somovie.data.database.entity.ActorEntity
import com.louisfn.somovie.data.database.relation.ActorWithPerson
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class ActorDao : BaseDao<ActorEntity>(TABLE_ACTOR) {

    @Transaction
    @Query("SELECT * FROM $TABLE_ACTOR WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract fun changes(movieId: Long): Flow<List<ActorWithPerson>>

    @Query("DELETE FROM $TABLE_ACTOR WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract fun delete(movieId: Long)
}
