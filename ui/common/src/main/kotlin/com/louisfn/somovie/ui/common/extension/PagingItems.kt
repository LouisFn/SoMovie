package com.louisfn.somovie.ui.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.louisfn.somovie.core.common.extension.takeAs

val LoadState.isLoading
    get() = this is LoadState.Loading

val LoadState.isNotLoading
    get() = this is LoadState.NotLoading

val LoadState.isError
    get() = this is LoadState.Error

@Composable
fun PagingItemsLoadStateErrorEffect(
    pagingItems: LazyPagingItems<*>,
    onRefreshError: ((Throwable) -> Unit)? = null,
    onAppendError: ((Throwable) -> Unit)? = null,
    onPrependError: ((Throwable) -> Unit)? = null
) {
    val currentOnRefreshError by rememberUpdatedState(onRefreshError)
    val currentOnAppendError by rememberUpdatedState(onAppendError)
    val currentOnPrependError by rememberUpdatedState(onPrependError)

    LaunchedEffect(pagingItems.loadState) {
        pagingItems.loadState.refresh.takeAs<LoadState.Error>()?.let {
            currentOnRefreshError?.invoke(it.error)
        }
        pagingItems.loadState.append.takeAs<LoadState.Error>()?.let {
            currentOnAppendError?.invoke(it.error)
        }
        pagingItems.loadState.prepend.takeAs<LoadState.Error>()?.let {
            currentOnPrependError?.invoke(it.error)
        }
    }
}
