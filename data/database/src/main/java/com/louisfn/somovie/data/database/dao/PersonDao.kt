package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import com.louisfn.somovie.data.database.TABLE_PERSON
import com.louisfn.somovie.data.database.entity.PersonEntity

@Dao
internal abstract class PersonDao : BaseDao<PersonEntity>(TABLE_PERSON)
