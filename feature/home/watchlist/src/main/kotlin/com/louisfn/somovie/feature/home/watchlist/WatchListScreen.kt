@file:OptIn(ExperimentalMaterialApi::class)

package com.louisfn.somovie.feature.home.watchlist

import android.content.res.Resources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.feature.home.watchlist.WatchlistAction.ShowUndoSwipeToDismissSnackbar
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.ContentState
import com.louisfn.somovie.feature.home.watchlist.WatchlistUiState.AccountLoggedIn.LoadNextPageState
import com.louisfn.somovie.feature.login.LogInLayout
import com.louisfn.somovie.feature.login.LogInManager
import com.louisfn.somovie.feature.login.LogInViewModel
import com.louisfn.somovie.ui.common.R
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.component.AutosizeText
import com.louisfn.somovie.ui.component.DefaultAsyncImage
import com.louisfn.somovie.ui.component.DefaultSnackbar
import com.louisfn.somovie.ui.component.DefaultTopAppBar
import com.louisfn.somovie.ui.component.IndeterminateProgressIndicator
import com.louisfn.somovie.ui.component.Retry
import com.louisfn.somovie.ui.component.TextRetryButton
import com.louisfn.somovie.ui.theme.Dimens
import com.louisfn.somovie.ui.theme.Typography
import com.louisfn.somovie.ui.theme.customColors
import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.VisibleForTesting
import com.louisfn.somovie.ui.common.R as commonR

private val MovieItemHeight = 96.dp

@Composable
@Suppress("ViewModelForwarding")
internal fun WatchlistScreen(
    viewModel: WatchlistViewModel = hiltViewModel(),
    logInViewModel: LogInViewModel = hiltViewModel(),
    showDetails: (Movie) -> Unit
) {
    val pagingItems = viewModel.pagedMovieItems.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsStateLifecycleAware()
    val scaffoldState = rememberScaffoldState()

    DisposableEffect(Unit) {
        onDispose { viewModel.onViewHidden() }
    }
    LaunchedEffect(pagingItems.loadState, pagingItems.itemCount) {
        viewModel.onLoadStateChanged(pagingItems.loadState, pagingItems.itemCount)
    }

    WatchlistScreen(
        uiState = uiState,
        action = viewModel.action,
        pagingItems = pagingItems,
        scaffoldState = scaffoldState,
        logInManager = logInViewModel,
        onMovieClick = showDetails,
        onMovieSwiped = viewModel::onSwipeToDismiss,
        onSnackbarActionPerformed = viewModel::onUndoSwipeToDismissSnackbarActionPerformed,
        onSnackbarDismissed = viewModel::onUndoSwipeToDismissSnackbarDismissed
    )
}

@Composable
internal fun WatchlistScreen(
    uiState: WatchlistUiState,
    action: Flow<WatchlistAction>,
    pagingItems: LazyPagingItems<MovieItem>,
    scaffoldState: ScaffoldState,
    logInManager: LogInManager,
    onMovieClick: (Movie) -> Unit,
    onMovieSwiped: (Movie) -> Unit,
    onSnackbarActionPerformed: (movieId: Long) -> Unit,
    onSnackbarDismissed: (movieId: Long) -> Unit
) {
    val resources = LocalContext.current.resources
    LaunchedEffect(Unit) {
        action
            .collect { action ->
                when (action) {
                    is ShowUndoSwipeToDismissSnackbar ->
                        scaffoldState.snackbarHostState.showCancelSwipeToDismissSnackbar(
                            resources = resources,
                            onSnackbarActionPerformed = { onSnackbarActionPerformed(action.movieId) },
                            onSnackbarDismissed = { onSnackbarDismissed(action.movieId) }
                        )
                }
            }
    }

    WatchlistScreen(
        uiState = uiState,
        pagingItems = pagingItems,
        scaffoldState = scaffoldState,
        logInManager = logInManager,
        onMovieClick = onMovieClick,
        onMovieSwiped = onMovieSwiped
    )
}

@Composable
internal fun WatchlistScreen(
    uiState: WatchlistUiState,
    pagingItems: LazyPagingItems<MovieItem>,
    scaffoldState: ScaffoldState,
    logInManager: LogInManager,
    onMovieClick: (Movie) -> Unit,
    onMovieSwiped: (Movie) -> Unit
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = { DefaultTopAppBar(text = stringResource(id = R.string.home_watchlist)) },
        snackbarHost = {
            SnackbarHost(it) { data ->
                DefaultSnackbar(snackbarData = data)
            }
        }
    ) {
        when (uiState) {
            is WatchlistUiState.AccountLoggedIn -> WatchlistContent(
                uiState = uiState,
                pagingItems = pagingItems,
                contentPadding = it,
                onMovieClick = onMovieClick,
                onMovieSwiped = onMovieSwiped
            )
            is WatchlistUiState.AccountDisconnected ->
                LogInContent(
                    logInManager = logInManager,
                    modifier = Modifier.fillMaxSize()
                )
            is WatchlistUiState.None -> Unit
        }
    }
}

@Composable
@Suppress("LambdaParameterNaming")
private fun LogInContent(logInManager: LogInManager, modifier: Modifier = Modifier) {
    LogInLayout(
        logInManager = logInManager,
        modifier = modifier,
        content = { Button ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(id = commonR.string.watchlist_log_in_description),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                )
                Button(modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}

@Composable
private fun WatchlistContent(
    uiState: WatchlistUiState.AccountLoggedIn,
    pagingItems: LazyPagingItems<MovieItem>,
    contentPadding: PaddingValues,
    onMovieClick: (Movie) -> Unit,
    onMovieSwiped: (Movie) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTag = WatchlistTestTag.WatchlistContent }
    ) {
        when (uiState.contentState) {
            ContentState.RETRY -> Retry(
                modifier = Modifier.align(Alignment.Center),
                onClick = pagingItems::retry
            )
            ContentState.LOADING ->
                IndeterminateProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            else -> WatchlistLazyColumn(
                pagingItems = pagingItems,
                loadNextPageState = uiState.loadNextPageState,
                contentPadding = contentPadding,
                onMovieClick = onMovieClick,
                onMovieSwiped = onMovieSwiped
            )
        }
    }
}

@Composable
private fun WatchlistLazyColumn(
    pagingItems: LazyPagingItems<MovieItem>,
    loadNextPageState: LoadNextPageState,
    contentPadding: PaddingValues,
    onMovieClick: (Movie) -> Unit,
    onMovieSwiped: (Movie) -> Unit
) {
    // https://issuetracker.google.com/issues/177245496#comment23
    if (pagingItems.itemCount == 0) return

    LazyColumn(
        modifier = Modifier
            .semantics { testTag = WatchlistTestTag.LazyColum },
        contentPadding = contentPadding
    ) {
        item {
            Divider(color = MaterialTheme.colors.onBackground)
        }
        items(
            count = pagingItems.itemCount,
            key = pagingItems.itemKey(key = { it.movie.id })
        ) { index ->
            val movieItem = pagingItems[index]
            Column {
                if (movieItem != null) {
                    WatchlistMovieItem(
                        movie = movieItem.movie,
                        isHidden = movieItem.isHidden,
                        onClick = { onMovieClick(movieItem.movie) },
                        onSwiped = { onMovieSwiped(movieItem.movie) }
                    )
                    if (!movieItem.isHidden) {
                        Divider(color = MaterialTheme.colors.onBackground)
                    }
                } else {
                    WatchlistMovieItemPlaceholder()
                    Divider(color = MaterialTheme.colors.onBackground)
                }
            }
        }

        if (loadNextPageState == LoadNextPageState.LOADING) {
            item {
                BottomLoader(modifier = Modifier.fillMaxWidth())
            }
        }

        if (loadNextPageState == LoadNextPageState.RETRY) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    TextRetryButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = pagingItems::retry
                    )
                }
            }
        }
    }
}

@Composable
private fun WatchlistMovieItemPlaceholder() {
    Row(
        modifier = Modifier
            .height(MovieItemHeight)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(Dimens.POSTER_RATIO)
                .background(MaterialTheme.colors.primary)
        )

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(with(LocalDensity.current) { Typography.subtitle1.fontSize.toDp() })
                    .fillMaxWidth()
                    .padding(end = 72.dp)
                    .background(MaterialTheme.customColors.placeholder)
            )
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxSize()
                    .background(MaterialTheme.customColors.placeholder)
            )
        }
    }
}

@Composable
@VisibleForTesting
@Suppress("ModifierMissing")
fun WatchlistMovieItem(
    movie: Movie,
    isHidden: Boolean,
    onClick: () -> Unit,
    onSwiped: () -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd) {
                onSwiped()
            }
            true
        }
    )

    LaunchedEffect(Unit) {
        if (!isHidden) {
            dismissState.snapTo(DismissValue.Default)
        }
    }

    LaunchedEffect(isHidden) {
        if (!isHidden && dismissState.isDismissed(DismissDirection.StartToEnd)) {
            dismissState.reset()
        }
    }

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.StartToEnd),
        background = {
            if (!isHidden) {
                WatchlistMovieItemDismissedBackground(dismissState)
            }
        },
        dismissContent = {
            AnimatedVisibility(
                visible = !isHidden,
                enter = slideInHorizontally { it },
                exit = shrinkVertically() + fadeOut()
            ) {
                WatchlistMovieItemContent(movie = movie, onClick = onClick)
            }
        }
    )
}

@Composable
private fun WatchlistMovieItemDismissedBackground(dismissState: DismissState) {
    Box(
        modifier = Modifier
            .semantics { testTag = WatchlistTestTag.MovieItemDismissedBackground }
            .height(MovieItemHeight)
            .fillMaxWidth()
            .background(MaterialTheme.colors.error)
    ) {
        Icon(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(36.dp)
                .align(
                    when (dismissState.dismissDirection) {
                        DismissDirection.StartToEnd -> Alignment.CenterStart
                        DismissDirection.EndToStart -> Alignment.CenterEnd
                        else -> Alignment.Center
                    }
                ),
            imageVector = Icons.Default.Delete,
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = ""
        )
    }
}

@Composable
private fun WatchlistMovieItemContent(movie: Movie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .semantics { testTag = WatchlistTestTag.MovieItemContent }
            .height(MovieItemHeight)
            .background(MaterialTheme.colors.background)
            .clickable(onClick = onClick)
    ) {
        DefaultAsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(Dimens.POSTER_RATIO),
            model = movie.posterPath
        )

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AutosizeText(
                text = movie.title,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 2
            )
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.caption,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun BottomLoader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IndeterminateProgressIndicator()
    }
}

private suspend fun SnackbarHostState.showCancelSwipeToDismissSnackbar(
    resources: Resources,
    onSnackbarActionPerformed: () -> Unit,
    onSnackbarDismissed: () -> Unit
) {
    val result = showSnackbar(
        message = resources.getString(R.string.watchlist_remove_from_watchlist_confirm_message),
        actionLabel = resources.getString(R.string.watchlist_remove_from_watchlist_action)
    )
    when (result) {
        SnackbarResult.ActionPerformed -> onSnackbarActionPerformed()
        SnackbarResult.Dismissed -> onSnackbarDismissed()
    }
}
