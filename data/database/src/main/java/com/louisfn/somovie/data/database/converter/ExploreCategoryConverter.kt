package com.louisfn.somovie.data.database.converter

import androidx.room.TypeConverter
import com.louisfn.somovie.data.database.entity.ExploreEntity

internal object ExploreCategoryConverter {

    @TypeConverter
    fun fromValue(value: Int): ExploreEntity.Category =
        ExploreEntity.Category.values().first { it.value == value }

    @TypeConverter
    fun toValue(category: ExploreEntity.Category): Int =
        category.value
}
