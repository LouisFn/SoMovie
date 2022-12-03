package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.database.relation.CrewMemberWithPerson
import com.louisfn.somovie.domain.model.CrewMember
import com.louisfn.somovie.domain.model.ProfilePath
import javax.inject.Inject

class CrewMemberMapper @Inject constructor() {

    //region Map entity to domain

    fun mapToDomain(entities: List<CrewMemberWithPerson>): List<CrewMember> = entities.map(::mapToDomain)

    fun mapToDomain(entity: CrewMemberWithPerson) = CrewMember(
        id = entity.person.id,
        name = entity.person.name,
        profilePath = entity.person.profilePath?.let(::ProfilePath),
        popularity = entity.person.popularity,
        job = entity.crewMember.job,
        department = entity.crewMember.department
    )

    //endregion
}
