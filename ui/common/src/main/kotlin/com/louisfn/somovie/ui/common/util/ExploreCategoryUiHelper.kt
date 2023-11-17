package com.louisfn.somovie.ui.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.ExploreCategory.*
import com.louisfn.somovie.ui.common.R as commonR

object ExploreCategoryUiHelper {

    val ExploreCategory.label: String
        @Composable
        get() = stringResource(
            id = when (this) {
                POPULAR -> commonR.string.explore_section_popular
                NOW_PLAYING -> commonR.string.explore_section_now_playing
                TOP_RATED -> commonR.string.explore_section_top_rated
                UPCOMING -> commonR.string.explore_section_upcoming
            },
        )

    val ExploreCategory.canDisplayVotes: Boolean
        get() = this != UPCOMING
}
