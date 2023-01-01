package com.louisfn.somovie.feature.navigation

import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Left
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Right
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.louisfn.somovie.feature.home.container.homeGraph
import com.louisfn.somovie.feature.moviedetails.MovieDetailsNavigation
import com.louisfn.somovie.feature.moviedetails.movieDetailsGraph
import com.louisfn.somovie.feature.movielist.MovieListNavigation
import com.louisfn.somovie.feature.movielist.movieListGraph
import com.louisfn.somovie.ui.common.navigation.NavigationDestination

private const val TransitionDuration = 700

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavHost(startDestination: NavigationDestination, modifier: Modifier = Modifier) {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        modifier = modifier
            .background(MaterialTheme.colors.background),
        navController = navController,
        startDestination = startDestination.route,
        enterTransition = { slideIntoContainer(Left, tween(TransitionDuration)) },
        exitTransition = { fadeOut(tween(TransitionDuration)) },
        popEnterTransition = { fadeIn(tween(TransitionDuration)) },
        popExitTransition = { slideOutOfContainer(Right, tween(TransitionDuration)) }
    ) {
        homeGraph(
            showDetails = { navController.navigate(MovieDetailsNavigation.createRoute(it.id)) },
            showMore = { navController.navigate(MovieListNavigation.createRoute(it)) }
        )
        movieDetailsGraph(
            navigateUp = navController::popBackStack
        )
        movieListGraph(
            showDetail = { navController.navigate(MovieDetailsNavigation.createRoute(it.id)) },
            navigateUp = navController::popBackStack
        )
    }
}
