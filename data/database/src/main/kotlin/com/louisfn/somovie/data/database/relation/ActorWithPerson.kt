package com.louisfn.somovie.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.louisfn.somovie.data.database.COLUMN_FK_PERSON_ID
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.entity.ActorEntity
import com.louisfn.somovie.data.database.entity.PersonEntity

data class ActorWithPerson(
    @Embedded
    val actor: ActorEntity,

    @Relation(
        parentColumn = COLUMN_FK_PERSON_ID,
        entityColumn = COLUMN_ID,
    )
    val person: PersonEntity,
)
