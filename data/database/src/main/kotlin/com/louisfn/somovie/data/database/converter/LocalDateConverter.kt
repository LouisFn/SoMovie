package com.louisfn.somovie.data.database.converter

import androidx.room.TypeConverter
import java.time.LocalDate

internal object LocalDateConverter {

    @TypeConverter
    fun fromValue(value: Long): LocalDate =
        LocalDate.ofEpochDay(value)

    @TypeConverter
    fun toValue(localDate: LocalDate): Long =
        localDate.toEpochDay()
}
