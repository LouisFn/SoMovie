package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import com.louisfn.somovie.data.database.TABLE_GENRE
import com.louisfn.somovie.data.database.entity.GenreEntity

@Dao
internal abstract class GenreDao : BaseDao<GenreEntity>(TABLE_GENRE)
