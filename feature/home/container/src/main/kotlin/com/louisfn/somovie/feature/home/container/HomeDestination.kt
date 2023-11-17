package com.louisfn.somovie.feature.home.container

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route: String = "home"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.homeGraph(
    showDetails: (Movie) -> Unit,
    showMore: (ExploreCategory) -> Unit,
) {
    composable(route = HomeDestination.route) {
        HomeScreen(
            showDetails = showDetails,
            showMore = showMore,
        )
    }
}
