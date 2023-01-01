package com.louisfn.somovie.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.louisfn.somovie.data.database.COLUMN_ID
import com.louisfn.somovie.data.database.COLUMN_LOGO_URL
import com.louisfn.somovie.data.database.COLUMN_NAME
import com.louisfn.somovie.data.database.TABLE_COMPANY

@Entity(tableName = TABLE_COMPANY)
data class CompanyEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_LOGO_URL)
    val logoPath: String?
)
