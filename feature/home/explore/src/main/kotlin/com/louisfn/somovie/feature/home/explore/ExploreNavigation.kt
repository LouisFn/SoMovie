package com.louisfn.somovie.feature.home.explore

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.feature.home.common.HomeItemState
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object ExploreNavigation : NavigationDestination {

    override val route: String = "explore"
}

fun NavGraphBuilder.exploreGraph(
    homeItemState: HomeItemState,
    showDetail: (Movie) -> Unit,
    showMore: (ExploreCategory) -> Unit
) {
    composable(route = ExploreNavigation.route) {
        ExploreScreen(
            homeItemState = homeItemState,
            showDetail = showDetail,
            showMore = showMore
        )
    }
}
