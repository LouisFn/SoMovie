package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.database.relation.ActorWithPerson
import com.louisfn.somovie.domain.model.Actor
import com.louisfn.somovie.domain.model.ProfilePath
import javax.inject.Inject

class ActorMapper @Inject constructor() {

    //region Map entity to domain

    fun mapToDomain(entities: List<ActorWithPerson>): List<Actor> = entities.map(::mapToDomain)

    fun mapToDomain(entity: ActorWithPerson) = Actor(
        id = entity.person.id,
        name = entity.person.name,
        profilePath = entity.person.profilePath?.let(::ProfilePath),
        popularity = entity.person.popularity,
        character = entity.actor.character,
        order = entity.actor.order
    )

    //endregion
}
