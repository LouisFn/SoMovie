package com.louisfn.somovie.feature.home.watchlist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object WatchlistDestination : NavigationDestination {

    override val route: String = "watchlist"
}

fun NavGraphBuilder.watchListGraph(
    showDetails: (Movie) -> Unit
) {
    composable(route = WatchlistDestination.route) {
        WatchlistScreen(
            showDetails = showDetails
        )
    }
}
