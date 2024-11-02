package com.louisfn.somovie.feature.home.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import com.louisfn.somovie.feature.home.discover.DiscoverUiState.Discover.LogInSnackbarState
import com.louisfn.somovie.feature.home.discover.DiscoverUiState.MovieItem
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.common.extension.pxToDp
import com.louisfn.somovie.ui.common.extension.top
import com.louisfn.somovie.ui.common.model.ImmutableList
import com.louisfn.somovie.ui.common.modifier.shake
import com.louisfn.somovie.ui.component.AutosizeText
import com.louisfn.somovie.ui.component.DefaultAsyncImage
import com.louisfn.somovie.ui.component.DefaultSnackbar
import com.louisfn.somovie.ui.component.IndeterminateProgressIndicator
import com.louisfn.somovie.ui.component.Retry
import com.louisfn.somovie.ui.component.swipe.SwipeContainer
import com.louisfn.somovie.ui.component.swipe.SwipeDirection
import com.louisfn.somovie.ui.common.R as commonR

private const val MaxMovieItemToPreload = 5
private const val SwipeFractionalThreshold = 0.25f

@Composable
internal fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    showAccount: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateLifecycleAware()

    DiscoverScreen(
        uiState = uiState,
        onSwipe = viewModel::onMovieSwipe,
        onDisappear = viewModel::onMovieDisappeared,
        retry = { viewModel.retry() },
        onLogInSnackbarActionClick = { showAccount() },
    )
}

@Composable
private fun DiscoverScreen(
    uiState: DiscoverUiState,
    onSwipe: (MovieItem, SwipeDirection) -> Unit,
    onDisappear: (MovieItem) -> Unit,
    retry: () -> Unit,
    onLogInSnackbarActionClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is DiscoverUiState.Discover -> DiscoverContent(
                items = uiState.items,
                logInSnackbarState = uiState.logInSnackbarState,
                onSwipe = onSwipe,
                onDisappear = onDisappear,
                onLogInSnackbarActionClick = onLogInSnackbarActionClick,
            )
            is DiscoverUiState.Retry -> Retry(
                modifier = Modifier.align(Alignment.Center),
                onClick = retry,
            )
            is DiscoverUiState.Loading -> IndeterminateProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
            )
            is DiscoverUiState.None -> Unit
        }
    }
}

@Composable
private fun BoxScope.DiscoverContent(
    items: ImmutableList<MovieItem>,
    logInSnackbarState: LogInSnackbarState,
    onSwipe: (MovieItem, SwipeDirection) -> Unit,
    onDisappear: (MovieItem) -> Unit,
    onLogInSnackbarActionClick: () -> Unit,
) {
    DiscoverSwipeContainer(
        items = items,
        onSwipe = onSwipe,
        onDisappear = onDisappear,
    )
    if (logInSnackbarState != LogInSnackbarState.HIDDEN) {
        DefaultSnackbar(
            message = stringResource(id = commonR.string.discover_log_in_description),
            actionLabel = stringResource(id = commonR.string.discover_log_in_action),
            onActionClick = onLogInSnackbarActionClick,
            modifier = Modifier
                .shake(logInSnackbarState == LogInSnackbarState.SHAKING)
                .align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun BoxScope.DiscoverSwipeContainer(
    items: ImmutableList<MovieItem>,
    onSwipe: (MovieItem, SwipeDirection) -> Unit,
    onDisappear: (MovieItem) -> Unit,
) {
    var draggingState by remember { mutableStateOf<DraggingState?>(null) }

    SwipeContainer(
        items = ImmutableList(items.take(MaxMovieItemToPreload)),
        itemKey = MovieItem::id,
        fractionalThreshold = SwipeFractionalThreshold,
        onDragging = { _, direction, ratio ->
            draggingState = DraggingState(direction, ratio)
        },
        onCancel = { draggingState = null },
        onSwipe = { item, direction ->
            draggingState = null
            onSwipe(item, direction)
        },
        onDisappear = { item, _ -> onDisappear(item) },
    ) { item ->
        DiscoverMovieItem(item)
    }

    draggingState?.let { DiscoverSwipeIcon(it) }
}

@Composable
private fun BoxScope.DiscoverSwipeIcon(draggingState: DraggingState) {
    Icon(
        modifier = Modifier
            .align(Alignment.Center)
            .size(draggingState.iconSize)
            .clip(CircleShape)
            .background(draggingState.iconBackgroundColor),
        imageVector = draggingState.icon,
        contentDescription = null,
    )
}

@Composable
private fun DiscoverMovieItem(item: MovieItem) {
    var isImageLoaded by remember { mutableStateOf(false) }

    Box {
        DefaultAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentScale = ContentScale.Crop,
            model = item.posterPath,
            onState = { isImageLoaded = it is AsyncImagePainter.State.Success },
        )
        AutosizeText(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background.copy(alpha = 0.8f))
                .padding(top = WindowInsets.statusBars.top.pxToDp())
                .padding(horizontal = 24.dp, vertical = 8.dp),
            text = item.title,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
        if (!isImageLoaded) {
            IndeterminateProgressIndicator(
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.Center),
            )
        }
    }
}

private data class DraggingState(
    val direction: SwipeDirection,
    val ratio: Float,
) {

    val icon: ImageVector
        @Composable
        get() = when (direction) {
            SwipeDirection.LEFT -> Icons.Default.Clear
            SwipeDirection.RIGHT -> Icons.Default.Add
        }

    val iconBackgroundColor: Color
        @Composable
        get() = when (direction) {
            SwipeDirection.LEFT -> MaterialTheme.colors.discoverDisliked
            SwipeDirection.RIGHT -> MaterialTheme.colors.discoverLiked
        }

    val iconSize: Dp =
        (MAX_ICON_SIZE * ratio / SwipeFractionalThreshold).coerceAtMost(MAX_ICON_SIZE)

    companion object {
        private val MAX_ICON_SIZE = 64.dp
    }
}
