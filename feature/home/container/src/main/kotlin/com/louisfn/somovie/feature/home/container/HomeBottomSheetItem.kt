package com.louisfn.somovie.feature.home.container

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.ui.graphics.vector.ImageVector
import com.louisfn.somovie.feature.home.account.AccountNavigation
import com.louisfn.somovie.feature.home.container.HomeBottomSheetItem.*
import com.louisfn.somovie.feature.home.discover.DiscoverNavigation
import com.louisfn.somovie.feature.home.explore.ExploreNavigation
import com.louisfn.somovie.feature.home.watchlist.WatchlistDestination
import com.louisfn.somovie.ui.common.R
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

sealed class HomeBottomSheetItem(
    val destination: NavigationDestination,
    @StringRes val titleId: Int,
    val icon: ImageVector,
) {
    object Explore : HomeBottomSheetItem(ExploreNavigation, R.string.home_explore, Icons.Default.Movie)
    object WatchList : HomeBottomSheetItem(WatchlistDestination, R.string.home_watchlist, Icons.Default.List)
    object Discover : HomeBottomSheetItem(DiscoverNavigation, R.string.home_discover, Icons.Default.Swipe)
    object Account : HomeBottomSheetItem(AccountNavigation, R.string.home_account, Icons.Default.Settings)
}

val HomeBottomSheetItems = listOf(Explore, WatchList, Discover, Account)
