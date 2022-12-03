package com.louisfn.somovie.feature.home.discover

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object DiscoverNavigation : NavigationDestination {

    override val route: String = "discover"
}

fun NavGraphBuilder.discoverGraph() {
    composable(route = DiscoverNavigation.route) {
        DiscoverScreen()
    }
}
