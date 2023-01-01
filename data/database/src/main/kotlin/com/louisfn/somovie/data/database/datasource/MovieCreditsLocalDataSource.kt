package com.louisfn.somovie.data.database.datasource

import androidx.annotation.AnyThread
import androidx.room.withTransaction
import com.louisfn.somovie.data.database.AppDatabase
import com.louisfn.somovie.data.database.relation.ActorWithPerson
import com.louisfn.somovie.data.database.relation.CrewMemberWithPerson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MovieCreditsLocalDataSource {

    @AnyThread
    fun actorsChanges(movieId: Long): Flow<List<ActorWithPerson>>

    @AnyThread
    fun crewMembersChanges(movieId: Long): Flow<List<CrewMemberWithPerson>>

    @AnyThread
    suspend fun replaceActors(movieId: Long, actorsWithPerson: List<ActorWithPerson>)

    @AnyThread
    suspend fun replaceCrewMembers(movieId: Long, crewMembersWithPerson: List<CrewMemberWithPerson>)
}

internal class DefaultMovieCreditsLocalDataSource @Inject constructor(
    private val database: AppDatabase
) : MovieCreditsLocalDataSource {

    override fun actorsChanges(movieId: Long): Flow<List<ActorWithPerson>> =
        database.actorDao().changes(movieId)

    override fun crewMembersChanges(movieId: Long): Flow<List<CrewMemberWithPerson>> =
        database.crewMemberDao().changes(movieId)

    override suspend fun replaceActors(movieId: Long, actorsWithPerson: List<ActorWithPerson>) =
        database.withTransaction {
            with(database) {
                personDao().insertOrIgnore(actorsWithPerson.map { it.person })
                with(actorDao()) {
                    delete(movieId)
                    insertOrAbort(actorsWithPerson.map { it.actor })
                }
            }
        }

    override suspend fun replaceCrewMembers(movieId: Long, crewMembersWithPerson: List<CrewMemberWithPerson>) =
        database.withTransaction {
            with(database) {
                personDao().insertOrIgnore(crewMembersWithPerson.map { it.person })
                with(crewMemberDao()) {
                    delete(movieId)
                    insertOrAbort(crewMembersWithPerson.map { it.crewMember })
                }
            }
        }
}
