package com.louisfn.somovie.feature.moviedetails.poster

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.louisfn.somovie.domain.model.PosterPath
import com.louisfn.somovie.feature.moviedetails.poster.MovieDetailsPosterState.EXPANDED
import com.louisfn.somovie.feature.moviedetails.poster.MovieDetailsPosterState.REDUCED
import com.louisfn.somovie.ui.common.extension.clickable
import com.louisfn.somovie.ui.common.extension.screenHeight
import com.louisfn.somovie.ui.common.extension.screenWidth
import com.louisfn.somovie.ui.common.extension.toDpSize
import com.louisfn.somovie.ui.common.extension.toIntOffset
import com.louisfn.somovie.ui.component.DefaultAsyncImage
import com.louisfn.somovie.ui.component.DefaultTopAppBar

@Composable
fun MovieDetailsPosterFullScreen(
    posterPath: PosterPath,
    posterReducedCoordinates: LayoutCoordinates,
    modifier: Modifier = Modifier,
) {
    var posterState by remember { mutableStateOf(REDUCED) }

    BackHandler(enabled = posterState == EXPANDED) { posterState = REDUCED }

    Box(modifier = modifier) {
        BackgroundWithTopBar(
            isVisible = posterState == EXPANDED,
            navigateUp = { posterState = REDUCED },
        )

        Poster(
            posterPath = posterPath,
            posterState = posterState,
            posterReducedCoordinates = posterReducedCoordinates,
            onPosterStateChanged = { posterState = it },
        )
    }
}

@Composable
private fun Poster(
    posterPath: PosterPath,
    posterState: MovieDetailsPosterState,
    posterReducedCoordinates: LayoutCoordinates,
    onPosterStateChanged: (MovieDetailsPosterState) -> Unit,
) {
    val posterStateController = rememberPosterStateController(
        reducedCoordinates = posterReducedCoordinates,
        onStateChanged = onPosterStateChanged,
    )
    var onLoadImageSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(posterState) { posterStateController.animateToState(posterState) }

    Poster(
        posterPath = posterPath,
        modifier = Modifier
            .size(posterStateController.size.toDpSize())
            .offset { posterStateController.offset.toIntOffset() }
            .clickable(withRipple = false) {
                if (onLoadImageSuccess) {
                    onPosterStateChanged(posterState.toggle())
                }
            }
            .draggablePoster(posterStateController),
        onLoadImageSuccess = { onLoadImageSuccess = true },
    )
}

@Composable
private fun Poster(
    posterPath: PosterPath,
    modifier: Modifier = Modifier,
    onLoadImageSuccess: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    DefaultAsyncImage(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp)),
        onSuccess = { onLoadImageSuccess() },
        model = ImageRequest
            .Builder(LocalContext.current)
            .size(
                width = configuration.screenWidth.toInt(),
                height = configuration.screenHeight.toInt(),
            )
            .data(posterPath)
            .build(),
    )
}

@Composable
private fun BackgroundWithTopBar(isVisible: Boolean, navigateUp: () -> Unit) {
    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {},
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
        ) {
            DefaultTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                backgroundColor = MaterialTheme.colors.background,
                navigateUp = navigateUp,
            )
        }
    }
}
