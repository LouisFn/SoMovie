package com.louisfn.somovie.ui.component.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.louisfn.somovie.domain.model.Movie
import com.louisfn.somovie.ui.component.DefaultAsyncImage
import com.louisfn.somovie.ui.theme.Dimens.POSTER_RATIO
import com.louisfn.somovie.ui.theme.customColors

private val MOVIE_CARD_WIDTH = 96.dp

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    showVotes: Boolean = movie.hasVotes,
    showDetail: (Movie) -> Unit
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .width(MOVIE_CARD_WIDTH)
            .clickable { showDetail(movie) }
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            DefaultAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(POSTER_RATIO),
                model = movie.posterPath
            )
            Text(
                text = movie.title,
                maxLines = 2,
                modifier = Modifier
                    .height(32.dp)
                    .padding(horizontal = 4.dp),
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption
            )
        }
        if (showVotes) {
            MovieVoteAverageChart(
                average = movie.voteAverage,
                modifier = Modifier
                    .padding(3.dp)
                    .align(Alignment.TopStart)
            )
        }
    }
}

@Composable
fun MovieCardPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .width(MOVIE_CARD_WIDTH)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(POSTER_RATIO)
                .background(MaterialTheme.customColors.placeholder)
        ) {}

        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .background(MaterialTheme.customColors.placeholder)
        ) {}
    }
}
