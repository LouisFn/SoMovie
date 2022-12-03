package com.louisfn.somovie.data.database.dao

import androidx.room.Dao
import com.louisfn.somovie.data.database.TABLE_COMPANY
import com.louisfn.somovie.data.database.entity.CompanyEntity

@Dao
internal abstract class CompanyDao : BaseDao<CompanyEntity>(TABLE_COMPANY)
