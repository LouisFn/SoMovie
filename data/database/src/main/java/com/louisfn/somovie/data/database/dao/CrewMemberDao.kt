package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.louisfn.somovie.data.database.COLUMN_FK_MOVIE_ID
import com.louisfn.somovie.data.database.TABLE_CREW_MEMBER
import com.louisfn.somovie.data.database.entity.CrewMemberEntity
import com.louisfn.somovie.data.database.relation.CrewMemberWithPerson
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class CrewMemberDao : BaseDao<CrewMemberEntity>(TABLE_CREW_MEMBER) {

    @Transaction
    @Query("SELECT * FROM $TABLE_CREW_MEMBER WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract fun changes(movieId: Long): Flow<List<CrewMemberWithPerson>>

    @Query("DELETE FROM $TABLE_CREW_MEMBER WHERE $COLUMN_FK_MOVIE_ID = :movieId")
    abstract fun delete(movieId: Long)
}
