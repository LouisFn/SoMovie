package com.louisfn.somovie.data.mapper

import com.louisfn.somovie.data.database.entity.ExploreEntity.Category
import com.louisfn.somovie.data.database.entity.RemoteKeyEntity.Type
import com.louisfn.somovie.domain.model.ExploreCategory
import javax.inject.Inject

class ExploreCategoryMapper @Inject constructor() {

    //region Map entity to domain

    fun mapToDomain(entity: Category): ExploreCategory = when (entity) {
        Category.POPULAR -> ExploreCategory.POPULAR
        Category.NOW_PLAYING -> ExploreCategory.NOW_PLAYING
        Category.TOP_RATED -> ExploreCategory.TOP_RATED
        Category.UPCOMING -> ExploreCategory.UPCOMING
    }

    fun mapToEntity(domain: ExploreCategory): Category = when (domain) {
        ExploreCategory.POPULAR -> Category.POPULAR
        ExploreCategory.NOW_PLAYING -> Category.NOW_PLAYING
        ExploreCategory.TOP_RATED -> Category.TOP_RATED
        ExploreCategory.UPCOMING -> Category.UPCOMING
    }

    //endregion

    fun mapToRemoteKeyTypeEntity(category: ExploreCategory) = when (category) {
        ExploreCategory.POPULAR -> Type.EXPLORE_POPULAR
        ExploreCategory.NOW_PLAYING -> Type.EXPLORE_NOW_PLAYING
        ExploreCategory.TOP_RATED -> Type.EXPLORE_TOP_RATED
        ExploreCategory.UPCOMING -> Type.EXPLORE_UPCOMING
    }
}
