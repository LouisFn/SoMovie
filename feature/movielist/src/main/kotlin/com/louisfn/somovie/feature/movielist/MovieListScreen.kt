package com.louisfn.somovie.feature.movielist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.feature.movielist.MovieListUiState.LoadNextPageState
import com.louisfn.somovie.ui.common.extension.PagingItemsLoadStateErrorEffect
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.common.extension.plus
import com.louisfn.somovie.ui.common.util.ExploreCategoryUiHelper.canDisplayVotes
import com.louisfn.somovie.ui.common.util.ExploreCategoryUiHelper.label
import com.louisfn.somovie.ui.component.DefaultTopAppBar
import com.louisfn.somovie.ui.component.IndeterminateProgressIndicator
import com.louisfn.somovie.ui.component.TextRetryButton
import com.louisfn.somovie.ui.component.movie.MovieCard
import com.louisfn.somovie.ui.component.movie.MovieCardPlaceholder
import com.louisfn.somovie.ui.theme.Dimens.DefaultScreenHorizontalPadding
import com.louisfn.somovie.ui.theme.Dimens.DefaultScreenVerticalPadding

private const val MovieGridNbrColumn = 3

@Composable
internal fun MovieListScreen(
    showDetail: (Movie) -> Unit,
    navigateUp: () -> Unit,
    viewModel: MovieListViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateLifecycleAware()
    val pagingItems = viewModel.pagedMovies.collectAsLazyPagingItems()

    PagingItemsLoadStateErrorEffect(
        pagingItems = pagingItems,
        onRefreshError = viewModel::onPagingError,
        onAppendError = viewModel::onPagingError,
    )

    LaunchedEffect(pagingItems.loadState, pagingItems) {
        viewModel.onLoadStateChanged(pagingItems.loadState)
    }

    MovieListScreen(
        uiState = state,
        pagingItems = pagingItems,
        showDetail = showDetail,
        navigateUp = navigateUp,
    )
}

@Composable
private fun MovieListScreen(
    uiState: MovieListUiState,
    pagingItems: LazyPagingItems<Movie>,
    showDetail: (Movie) -> Unit,
    navigateUp: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            DefaultTopAppBar(
                text = uiState.category.label,
                navigateUp = navigateUp,
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            MovieListLazyGrid(
                pagingItems = pagingItems,
                loadNextPageState = uiState.loadNextPageState,
                contentPadding = it,
                showVotes = uiState.category.canDisplayVotes,
                showDetail = showDetail,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MovieListLazyGrid(
    pagingItems: LazyPagingItems<Movie>,
    loadNextPageState: LoadNextPageState,
    contentPadding: PaddingValues,
    showVotes: Boolean,
    showDetail: (Movie) -> Unit,
) {
    // https://issuetracker.google.com/issues/177245496#comment23
    if (pagingItems.itemCount == 0) return

    LazyVerticalGrid(
        columns = GridCells.Fixed(MovieGridNbrColumn),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = contentPadding + PaddingValues(
            horizontal = DefaultScreenHorizontalPadding,
            vertical = DefaultScreenVerticalPadding,
        ),
    ) {
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey { it.id },
            contentType = pagingItems.itemContentType {},
        ) { index ->
            val movie = pagingItems[index]
            if (movie != null) {
                MovieCard(
                    movie = movie,
                    showVotes = showVotes,
                    showDetail = showDetail,
                )
            } else {
                MovieCardPlaceholder()
            }
        }

        if (loadNextPageState == LoadNextPageState.LOADING) {
            item(
                span = { GridItemSpan(MovieGridNbrColumn) },
            ) {
                Loader()
            }
        }

        if (loadNextPageState == LoadNextPageState.FAILED) {
            item(
                span = { GridItemSpan(MovieGridNbrColumn) },
            ) {
                TextRetryButton(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = pagingItems::retry,
                )
            }
        }
    }
}

@Composable
private fun Loader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        IndeterminateProgressIndicator()
    }
}
