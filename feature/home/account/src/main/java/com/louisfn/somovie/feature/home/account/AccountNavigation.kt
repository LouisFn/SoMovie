package com.louisfn.somovie.feature.home.account

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object AccountNavigation : NavigationDestination {

    override val route: String = "account"
}

fun NavGraphBuilder.accountGraph() {
    composable(route = AccountNavigation.route) {
        AccountScreen()
    }
}
