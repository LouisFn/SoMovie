package com.louisfn.somovie.feature.moviedetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.filled.PlaylistRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.louisfn.somovie.core.common.extension.safeLet
import com.louisfn.somovie.domain.model.BackdropPath
import com.louisfn.somovie.domain.model.MovieGenre
import com.louisfn.somovie.domain.model.PosterPath
import com.louisfn.somovie.feature.moviedetails.WatchlistFabState.WatchlistState
import com.louisfn.somovie.feature.moviedetails.poster.MovieDetailsPosterFullScreen
import com.louisfn.somovie.ui.common.extension.collectAsStateLifecycleAware
import com.louisfn.somovie.ui.common.model.ImmutableList
import com.louisfn.somovie.ui.component.IndeterminateProgressIndicator
import com.louisfn.somovie.ui.component.Retry
import com.louisfn.somovie.ui.theme.AppTheme
import java.time.Duration

@Composable
internal fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateLifecycleAware()

    MovieDetailsScreen(
        state = state,
        onRetryClick = viewModel::retry,
        onWatchlistFabClick = viewModel::switchWatchlistState,
        navigateUp = navigateUp,
    )
}

@Composable
private fun MovieDetailsScreen(
    state: MovieDetailsUiState,
    onRetryClick: () -> Unit,
    onWatchlistFabClick: () -> Unit,
    navigateUp: () -> Unit,
) {
    var posterReducedCoordinates by remember { mutableStateOf<LayoutCoordinates?>(null) }

    val headerUiState = state.headerUiState ?: return Box {}

    Box {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (header, content, watchlistFab) = createRefs()

            MovieDetailsHeader(
                headerUiState = headerUiState,
                modifier = Modifier.constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                navigateUp = navigateUp,
                onPosterPositioned = { posterReducedCoordinates = it },
            )

            Box(
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(header.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.fillToConstraints
                    },
            ) {
                state.contentUiState?.let {
                    MovieDetailsScreen(contentUiState = it, onRetryClick = onRetryClick)
                }
            }

            state.watchlistFabState?.let {
                AddToWatchlistSmallFab(
                    watchlistFabState = it,
                    modifier = Modifier
                        .constrainAs(watchlistFab) {
                            top.linkTo(header.bottom)
                            bottom.linkTo(header.bottom)
                            end.linkTo(parent.end, 12.dp)
                        },
                    onClick = onWatchlistFabClick,
                )
            }
        }

        safeLet(posterReducedCoordinates, state.headerUiState.posterPath) { coordinates, path ->
            MovieDetailsPosterFullScreen(
                posterPath = path,
                posterReducedCoordinates = coordinates,
            )
        }
    }
}

@Composable
private fun AddToWatchlistSmallFab(
    watchlistFabState: WatchlistFabState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(48.dp),
        contentPadding = PaddingValues(0.dp),
        enabled = !watchlistFabState.isLoading,
        shape = CircleShape,
        colors = ButtonDefaults
            .buttonColors(
                backgroundColor = when (watchlistFabState.state) {
                    WatchlistState.ADD_TO_WATCHLIST -> MaterialTheme.colors.secondary
                    WatchlistState.REMOVE_FROM_WATCHLIST -> MaterialTheme.colors.error
                },
            ),
    ) {
        Icon(
            imageVector = when (watchlistFabState.state) {
                WatchlistState.ADD_TO_WATCHLIST -> Icons.Filled.PlaylistAdd
                WatchlistState.REMOVE_FROM_WATCHLIST -> Icons.Filled.PlaylistRemove
            },
            tint = MaterialTheme.colors.onPrimary,
            contentDescription = "",
        )
    }
}

@Composable
private fun BoxScope.MovieDetailsScreen(contentUiState: ContentUiState, onRetryClick: () -> Unit) {
    when (contentUiState) {
        is ContentUiState.Content -> MovieDetailsScreen(state = contentUiState)
        is ContentUiState.Loading -> MovieDetailsLoader(
            modifier = Modifier.align(Alignment.Center),
        )
        is ContentUiState.Retry -> MovieDetailsRetry(
            modifier = Modifier.align(Alignment.Center),
            onClick = onRetryClick,
        )
    }
}

@Composable
private fun MovieDetailsLoader(modifier: Modifier = Modifier) {
    IndeterminateProgressIndicator(
        modifier = modifier,
    )
}

@Composable
private fun MovieDetailsRetry(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Retry(
        modifier = modifier,
        onClick = onClick,
    )
}

@Preview
@Composable
private fun MovieDetailScreenPreview() {
    AppTheme {
        MovieDetailsScreen(
            state = MovieDetailsUiState(
                headerUiState = HeaderUiState(
                    title = "Spider-Man: No Way Home",
                    posterPath = PosterPath("/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"),
                    backdropPaths = ImmutableList(
                        listOf(
                            BackdropPath("/iQFcwSGbZXMkeyKrxbPnwnRo5fl.jpg"),
                            BackdropPath("/iQFcwSGbZXMkeyKrxbPnwnRo5fp.jpg"),
                            BackdropPath("/iQFcwSGbZXMkeyKrxbPnwnRo5fs.jpg"),
                        ),
                    ),
                    tagline = "The Multiverse unleashed.",
                    voteAverage = 8.4f,
                    voteCount = 7997,
                    tmdbUrl = "",
                    releaseDate = "2021/12/15",
                ),
                contentUiState = ContentUiState.Content(
                    originalTitle = "Spider-Man: No Way Home",
                    originalLanguage = "en",
                    overview = "Peter Parker is unmasked and no longer able to separate his normal life from the " +
                        "high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes " +
                        "become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                    runtime = Duration.ofMillis(148),
                    popularity = 7683.216f,
                    budget = "200000000",
                    revenue = "1804372547",
                    crew = ImmutableList(emptyList()),
                    cast = ImmutableList(emptyList()),
                    genres = ImmutableList(
                        listOf(
                            MovieGenre(
                                id = 1,
                                name = "Action",
                            ),
                            MovieGenre(
                                id = 2,
                                name = "Adventure",
                            ),
                            MovieGenre(
                                id = 3,
                                name = "Science Fiction",
                            ),
                        ),
                    ),
                    videos = ImmutableList(),
                ),
                watchlistFabState = WatchlistFabState(
                    isLoading = true,
                    state = WatchlistState.REMOVE_FROM_WATCHLIST,
                ),
            ),
            onRetryClick = {},
            onWatchlistFabClick = {},
            navigateUp = {},
        )
    }
}
