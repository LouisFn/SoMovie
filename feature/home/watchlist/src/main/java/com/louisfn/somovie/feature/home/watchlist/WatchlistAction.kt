package com.louisfn.somovie.feature.home.watchlist

import com.louisfn.somovie.ui.common.base.ViewModelAction

sealed interface WatchlistAction : ViewModelAction {

    @JvmInline
    value class ShowUndoSwipeToDismissSnackbar(val movieId: Long) : WatchlistAction
}
