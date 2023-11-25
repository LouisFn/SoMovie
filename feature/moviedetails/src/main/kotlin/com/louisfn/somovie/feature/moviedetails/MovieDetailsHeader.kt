package com.louisfn.somovie.feature.moviedetails

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.louisfn.somovie.domain.model.BackdropPath
import com.louisfn.somovie.ui.common.LocalAppRouter
import com.louisfn.somovie.ui.common.model.ImmutableList
import com.louisfn.somovie.ui.component.AutosizeText
import com.louisfn.somovie.ui.component.DefaultTopAppBar
import com.louisfn.somovie.ui.component.movie.MovieVoteAverageChart
import com.louisfn.somovie.ui.theme.Dimens

private const val BackdropRatio = 1.778f

@Composable
internal fun MovieDetailsHeader(
    headerUiState: HeaderUiState,
    onPosterPositioned: (LayoutCoordinates) -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colors.background),
    ) {
        MovieDetailsHeaderContent(
            headerUiState = headerUiState,
            onPosterPositioned = onPosterPositioned,
        )
        MovieDetailsTopBar(
            shareUrl = headerUiState.tmdbUrl,
            navigateUp = navigateUp,
        )
    }
}

@Composable
private fun MovieDetailsTopBar(shareUrl: String, navigateUp: () -> Unit) {
    val router = LocalAppRouter.current
    MovieDetailsTopBar(
        share = { router.shareText(shareUrl) },
        navigateUp = navigateUp,
    )
}

@Composable
private fun MovieDetailsTopBar(share: () -> Unit, navigateUp: () -> Unit) {
    DefaultTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        backgroundColor = Color.Transparent,
        actions = {
            MovieDetailsIconButton(onClick = share) {
                Icon(
                    imageVector = Icons.Default.Share,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = "",
                )
            }
        },
        navigationIcon = {
            MovieDetailsIconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = MaterialTheme.colors.onPrimary,
                    contentDescription = "",
                )
            }
        },
    )
}

@Composable
private fun MovieDetailsIconButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary.copy(alpha = 0.8f)),
        onClick = onClick,
        content = content,
    )
}

@Composable
private fun MovieDetailsHeaderContent(
    headerUiState: HeaderUiState,
    onPosterPositioned: (LayoutCoordinates) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .wrapContentHeight()
            .shadow(8.dp)
            .background(MaterialTheme.colors.primary),
    ) {
        val (poster, backdropsPager, title, releaseDate, divider, tagline, voteAverage) = createRefs()

        BackdropsPager(
            modifier = Modifier
                .constrainAs(backdropsPager) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            backdropPaths = headerUiState.backdropPaths,
        )

        Box(
            modifier = Modifier
                .width(108.dp)
                .aspectRatio(Dimens.PosterRatio)
                .constrainAs(poster) {
                    top.linkTo(backdropsPager.bottom)
                    bottom.linkTo(backdropsPager.bottom)
                    start.linkTo(parent.start, 24.dp)
                }
                .onGloballyPositioned { onPosterPositioned(it) },
        )

        AutosizeText(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(backdropsPager.bottom, 8.dp)
                    linkTo(
                        start = poster.end,
                        end = voteAverage.start,
                        startMargin = 16.dp,
                        endMargin = 8.dp,
                        bias = 0f,
                    )
                    width = Dimension.fillToConstraints
                },
            text = headerUiState.title,
            style = MaterialTheme.typography.h6,
            maxLines = 2,
        )

        headerUiState.releaseDate?.let { date ->
            Text(
                modifier = Modifier
                    .constrainAs(releaseDate) {
                        top.linkTo(title.bottom)
                        start.linkTo(title.start)
                        linkTo(
                            start = title.start,
                            end = voteAverage.start,
                            endMargin = 8.dp,
                            bias = 0f,
                        )
                    },
                text = date,
                style = MaterialTheme.typography.body2,
            )
        }

        if (headerUiState.hasVotes) {
            VoteAverage(
                voteAverage = headerUiState.voteAverage,
                voteCount = headerUiState.voteCount,
                modifier = Modifier
                    .constrainAs(voteAverage) {
                        end.linkTo(parent.end, 8.dp)
                        top.linkTo(title.top)
                    },
            )
        }

        headerUiState.tagline?.let {
            Divider(
                modifier = Modifier
                    .constrainAs(divider) {
                        top.linkTo(poster.bottom, 16.dp)
                        start.linkTo(parent.start, 24.dp)
                        end.linkTo(parent.end, 24.dp)
                        width = Dimension.fillToConstraints
                    },
            )

            Text(
                modifier = Modifier
                    .constrainAs(tagline) {
                        top.linkTo(divider.bottom, 12.dp)
                        bottom.linkTo(parent.bottom, 12.dp)
                        start.linkTo(parent.start, 64.dp)
                        end.linkTo(parent.end, 64.dp)
                        width = Dimension.fillToConstraints
                    },
                text = headerUiState.tagline.orEmpty(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun BackdropsPager(
    backdropPaths: ImmutableList<BackdropPath>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        val pagerState = rememberPagerState { backdropPaths.size }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(BackdropRatio),
        ) { page ->
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = rememberAsyncImagePainter(backdropPaths[page]),
                contentDescription = "",
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            pageCount = backdropPaths.size,
            activeColor = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        )
    }
}

@Composable
private fun VoteAverage(voteAverage: Float, voteCount: Int?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MovieVoteAverageChart(
            modifier = Modifier
                .padding(4.dp)
                .size(48.dp),
            average = voteAverage,
            strokeWidth = 5.dp,
            textStyle = MaterialTheme.typography.subtitle1,
        )
        if (voteCount != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(12.dp),
                    imageVector = Icons.Filled.People,
                    tint = MaterialTheme.typography.overline.color,
                    contentDescription = "",
                )
                Text(
                    text = voteCount.toString(),
                    style = MaterialTheme.typography.overline,
                )
            }
        }
    }
}
