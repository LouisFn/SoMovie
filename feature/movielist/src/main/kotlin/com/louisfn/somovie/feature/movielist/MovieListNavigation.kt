package com.louisfn.somovie.feature.movielist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

object MovieListNavigation : NavigationDestination {
    const val ARGS_CATEGORY = "category"

    override val route: String = "movies/{$ARGS_CATEGORY}"

    fun createRoute(category: ExploreCategory) = "movies/${ExploreCategory.valueOf(category.name)}"
}

fun NavGraphBuilder.movieListGraph(
    showDetail: (Movie) -> Unit,
    navigateUp: () -> Unit,
) {
    composable(
        route = MovieListNavigation.route,
        arguments = listOf(
            navArgument(MovieListNavigation.ARGS_CATEGORY) { type = NavType.StringType },
        ),
    ) {
        MovieListScreen(
            showDetail = showDetail,
            navigateUp = navigateUp,
        )
    }
}
