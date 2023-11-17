package com.louisfn.somovie.domain.model

interface Person {
    val id: Long
    val name: String
    val profilePath: ProfilePath?
    val popularity: Float
}

data class Actor(
    override val id: Long,
    override val name: String,
    override val profilePath: ProfilePath?,
    override val popularity: Float,
    val character: String,
    val order: Int,
) : Person

data class CrewMember(
    override val id: Long,
    override val name: String,
    override val profilePath: ProfilePath?,
    override val popularity: Float,
    val job: String,
    val department: String,
) : Person
