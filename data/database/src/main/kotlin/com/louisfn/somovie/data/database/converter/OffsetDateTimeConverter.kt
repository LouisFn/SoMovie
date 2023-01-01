package com.louisfn.somovie.data.database.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

internal object OffsetDateTimeConverter {

    @TypeConverter
    fun fromValue(value: Long): OffsetDateTime =
        OffsetDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC)

    @TypeConverter
    fun toValue(offsetDateTime: OffsetDateTime): Long =
        offsetDateTime.toInstant().toEpochMilli()
}
