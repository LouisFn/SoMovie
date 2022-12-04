package com.louisfn.somovie.feature.home.container

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ui.BottomNavigation
import com.louisfn.somovie.common.logger.Logger
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.feature.home.account.accountGraph
import com.louisfn.somovie.feature.home.common.HomeItemState
import com.louisfn.somovie.feature.home.common.rememberHomeItemState
import com.louisfn.somovie.feature.home.discover.discoverGraph
import com.louisfn.somovie.feature.home.explore.exploreGraph
import com.louisfn.somovie.feature.home.watchlist.watchListGraph

@Composable
internal fun HomeScreen(
    showDetails: (Movie) -> Unit,
    showMore: (ExploreCategory) -> Unit
) {
    Logger.v("Navigation - HomeScreen")
    val navController = rememberNavController()
    val homeItemState = rememberHomeItemState()

    Column {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                startDestination = HomeBottomSheetItem.Explore.destination.route
            ) {
                exploreGraph(
                    homeItemState = homeItemState,
                    showDetail = showDetails,
                    showMore = showMore
                )
                watchListGraph(
                    showDetails = showDetails
                )
                discoverGraph(
                    showAccount = { navController.navigate(HomeBottomSheetItem.Account) }
                )
                accountGraph()
            }
        }
        BottomBar(
            navController = navController,
            homeItemState = homeItemState
        )
    }
}

@Composable
private fun BottomBar(
    navController: NavController,
    homeItemState: HomeItemState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomBar(
        currentDestination = currentDestination,
        onItemClick = { item ->
            if (item.destination.route != currentDestination?.route) {
                navController.navigate(item)
            } else {
                homeItemState.homeItemReselect()
            }
        }
    )
}

@Composable
private fun BottomBar(
    currentDestination: NavDestination?,
    onItemClick: (HomeBottomSheetItem) -> Unit
) {
    BottomNavigation(
        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
        elevation = 0.dp
    ) {
        HomeBottomSheetItems.forEach { item ->
            BottomNavigationItem(
                item = item,
                currentDestination = currentDestination,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavigationItem(
    item: HomeBottomSheetItem,
    currentDestination: NavDestination?,
    onItemClick: (HomeBottomSheetItem) -> Unit
) {
    val isCurrentDestination = currentDestination?.route == item.destination.route
    BottomNavigationItem(
        label = { Text(stringResource(id = item.titleId)) },
        icon = { Icon(item.icon, contentDescription = null) },
        selected = isCurrentDestination,
        onClick = { onItemClick(item) },
        selectedContentColor = MaterialTheme.colors.secondary,
        unselectedContentColor = MaterialTheme.colors.onPrimary
    )
}

private fun NavController.navigate(item: HomeBottomSheetItem) {
    navigate(item.destination.route) {
        popUpTo(graph.findStartDestination().id)
        launchSingleTop = true
    }
}
