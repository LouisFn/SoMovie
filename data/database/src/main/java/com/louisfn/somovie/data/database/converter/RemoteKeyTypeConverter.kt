package com.louisfn.somovie.data.database.converter

import androidx.room.TypeConverter
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity

internal object RemoteKeyTypeConverter {

    @TypeConverter
    fun fromValue(value: Int): RemoteKeyEntity.Type =
        RemoteKeyEntity.Type.values().first { it.value == value }

    @TypeConverter
    fun toValue(type: RemoteKeyEntity.Type): Int =
        type.value
}
