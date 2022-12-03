package com.louisfn.somovie.feature.moviedetails.poster

enum class MovieDetailsPosterState {
    EXPANDED,
    REDUCED;

    fun toggle(): MovieDetailsPosterState = when (this) {
        EXPANDED -> REDUCED
        REDUCED -> EXPANDED
    }
}
