package com.louisfn.somovie.feature.home.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.louisfn.somovie.domain.model.ExploreCategory
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.feature.home.common.HomeItemState
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.common.model.ImmutableList
import com.louisfn.somovie.ui.common.util.ExploreCategoryUiHelper.canDisplayVotes
import com.louisfn.somovie.ui.common.util.ExploreCategoryUiHelper.label
import com.louisfn.somovie.ui.component.DefaultTextButton
import com.louisfn.somovie.ui.component.DefaultTopAppBar
import com.louisfn.somovie.ui.component.IndeterminateProgressIndicator
import com.louisfn.somovie.ui.component.Retry
import com.louisfn.somovie.ui.component.movie.MovieCard
import com.louisfn.somovie.ui.theme.Dimens.DEFAULT_SCREEN_HORIZONTAL_PADDING
import kotlinx.coroutines.launch
import com.louisfn.somovie.ui.common.R as commonR

@Composable
internal fun ExploreScreen(
    homeItemState: HomeItemState,
    showDetail: (Movie) -> Unit,
    showMore: (ExploreCategory) -> Unit,
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateLifecycleAware()

    ExploreScreen(
        homeItemState = homeItemState,
        state = state,
        showDetail = showDetail,
        showMore = showMore,
        retry = viewModel::refresh,
    )
}

@Composable
private fun ExploreScreen(
    state: ExploreUiState,
    homeItemState: HomeItemState,
    showDetail: (Movie) -> Unit,
    showMore: (ExploreCategory) -> Unit,
    retry: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = { DefaultTopAppBar(text = stringResource(id = commonR.string.home_explore)) },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            ExploreContent(
                homeItemState = homeItemState,
                state = state,
                showDetail = showDetail,
                showMore = showMore,
                retry = retry,
            )
        }
    }
}

@Composable
private fun BoxScope.ExploreContent(
    state: ExploreUiState,
    homeItemState: HomeItemState,
    showDetail: (Movie) -> Unit,
    showMore: (ExploreCategory) -> Unit,
    retry: () -> Unit,
) {
    when (state) {
        is ExploreUiState.Loading ->
            IndeterminateProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
        is ExploreUiState.Retry -> Retry(
            modifier = Modifier.align(Alignment.Center),
            onClick = retry,
        )
        is ExploreUiState.Explore -> ExploreLazyColumn(
            homeItemState = homeItemState,
            moviesByCategory = state.movies,
            showDetail = showDetail,
            showMore = showMore,
        )
        is ExploreUiState.None -> Unit
    }
}

@Composable
private fun ExploreLazyColumn(
    homeItemState: HomeItemState,
    moviesByCategory: ImmutableList<Pair<ExploreCategory, ImmutableList<Movie>>>,
    showDetail: (Movie) -> Unit,
    showMore: (ExploreCategory) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 16.dp),
    ) {
        items(
            items = moviesByCategory,
            key = { it.first.ordinal },
        ) { (section, movies) ->
            Column {
                Section(exploreCategory = section, showMore = showMore)
                MoviesLazyRow(
                    homeItemState = homeItemState,
                    movies = movies,
                    showVotes = section.canDisplayVotes,
                    showDetail = showDetail,
                )
            }
        }
    }
}

@Composable
private fun Section(
    exploreCategory: ExploreCategory,
    showMore: (ExploreCategory) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = DEFAULT_SCREEN_HORIZONTAL_PADDING, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = SpaceBetween,
    ) {
        Text(
            text = exploreCategory.label,
            style = MaterialTheme.typography.h6,
        )
        DefaultTextButton(
            text = stringResource(commonR.string.explore_section_show_more),
            onClick = { showMore(exploreCategory) },
        )
    }
}

@Composable
private fun MoviesLazyRow(
    homeItemState: HomeItemState,
    movies: ImmutableList<Movie>,
    showVotes: Boolean,
    showDetail: (Movie) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    with(homeItemState) {
        DisposableEffect(this) {
            val listener = HomeItemState.OnHomeItemReselectListener {
                coroutineScope.launch { lazyListState.animateScrollToItem(0) }
            }
            addOnHomeItemReselectListener(listener)
            onDispose { removeOnHomeItemReselectListener(listener) }
        }
    }

    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = DEFAULT_SCREEN_HORIZONTAL_PADDING),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(
            items = movies,
            key = { it.id },
        ) { movie ->
            MovieCard(
                movie = movie,
                showDetail = showDetail,
                showVotes = showVotes,
            )
        }
    }
}
