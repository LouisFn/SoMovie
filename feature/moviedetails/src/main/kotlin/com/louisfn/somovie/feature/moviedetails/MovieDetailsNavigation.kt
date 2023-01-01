package com.louisfn.somovie.feature.moviedetails

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.composable
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object MovieDetailsNavigation : NavigationDestination {
    const val ARGS_MOVIE_ID = "movie_id"

    override val route: String = "movie/{$ARGS_MOVIE_ID}"

    fun createRoute(movieId: Long) = "movie/$movieId"
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.movieDetailsGraph(navigateUp: () -> Unit) {
    composable(
        route = MovieDetailsNavigation.route,
        arguments = listOf(
            navArgument(MovieDetailsNavigation.ARGS_MOVIE_ID) { type = NavType.LongType }
        )
    ) {
        MovieDetailsScreen(navigateUp = navigateUp)
    }
}
