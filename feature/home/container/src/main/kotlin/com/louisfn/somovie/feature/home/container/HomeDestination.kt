package com.louisfn.somovie.feature.home.container

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route: String = "home"
}

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
