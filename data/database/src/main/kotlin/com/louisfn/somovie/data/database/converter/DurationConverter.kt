package com.louisfn.somovie.data.database.converter

import androidx.room.TypeConverter
import java.time.Duration

internal object DurationConverter {

    @TypeConverter
    fun fromValue(value: Long): Duration =
        Duration.ofMillis(value)

    @TypeConverter
    fun toValue(duration: Duration): Long =
        duration.toMillis()
}
