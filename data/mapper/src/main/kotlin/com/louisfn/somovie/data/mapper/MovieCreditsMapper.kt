package com.louisfn.somovie.data.mapper

import androidx.annotation.AnyThread
import com.louisfn.somovie.data.database.entity.ActorEntity
import com.louisfn.somovie.data.database.entity.CrewMemberEntity
import com.louisfn.somovie.data.database.entity.PersonEntity
import com.louisfn.somovie.data.database.relation.ActorWithPerson
import com.louisfn.somovie.data.database.relation.CrewMemberWithPerson
import com.louisfn.somovie.data.network.response.MovieCreditsResponse
import com.louisfn.somovie.domain.model.Actor
import com.louisfn.somovie.domain.model.CrewMember
import com.louisfn.somovie.domain.model.MovieCredits
import com.louisfn.somovie.domain.model.ProfilePath
import javax.inject.Inject

class MovieCreditsMapper @Inject constructor() {

    //region Map entity to domain

    @AnyThread
    fun mapToDomain(
        actorEntities: List<ActorWithPerson>,
        crewMemberEntities: List<CrewMemberWithPerson>,
    ) =
        MovieCredits(
            crewMembers = crewMemberEntities.map { mapToDomain(it) },
            actors = actorEntities.map { mapToDomain(it) },
        )

    @AnyThread
    private fun mapToDomain(entity: ActorWithPerson) = Actor(
        id = entity.person.id,
        name = entity.person.name,
        profilePath = entity.person.profilePath?.let(::ProfilePath),
        popularity = entity.person.popularity,
        character = entity.actor.character,
        order = entity.actor.order,
    )

    @AnyThread
    private fun mapToDomain(entity: CrewMemberWithPerson) = CrewMember(
        id = entity.person.id,
        name = entity.person.name,
        profilePath = entity.person.profilePath?.let(::ProfilePath),
        popularity = entity.person.popularity,
        job = entity.crewMember.job,
        department = entity.crewMember.department,
    )

    //endregion

    //region Map response to entities

    @AnyThread
    fun mapCastToEntity(response: MovieCreditsResponse): List<ActorWithPerson> =
        response.cast.map { mapToEntity(response.movieId, it) }

    @AnyThread
    fun mapCrewToEntity(response: MovieCreditsResponse): List<CrewMemberWithPerson> =
        response.crew.map { mapToEntity(response.movieId, it) }

    @AnyThread
    private fun mapToEntity(
        movieId: Long,
        response: MovieCreditsResponse.CrewMember,
    ) = CrewMemberWithPerson(
        crewMember = CrewMemberEntity(
            job = response.job,
            department = response.department,
            personId = response.id,
            movieId = movieId,
        ),
        person = PersonEntity(
            id = response.id,
            name = response.name,
            profilePath = response.profilePath,
            popularity = response.popularity,
        ),
    )

    @AnyThread
    private fun mapToEntity(
        movieId: Long,
        response: MovieCreditsResponse.Actor,
    ) = ActorWithPerson(
        actor = ActorEntity(
            character = response.character,
            order = response.order,
            personId = response.id,
            movieId = movieId,
        ),
        person = PersonEntity(
            id = response.id,
            name = response.name,
            profilePath = response.profilePath,
            popularity = response.popularity,
        ),
    )

    //endregion
}
