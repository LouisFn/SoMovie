package com.louisfn.somovie.data.database.converter

import androidx.room.TypeConverter
import java.time.Instant

internal object InstantConverter {

    @TypeConverter
    fun fromValue(value: Long): Instant =
        Instant.ofEpochMilli(value)

    @TypeConverter
    fun toValue(instant: Instant): Long =
        instant.toEpochMilli()
}
