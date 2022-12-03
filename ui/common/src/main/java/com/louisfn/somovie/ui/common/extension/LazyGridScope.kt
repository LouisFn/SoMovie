package com.louisfn.somovie.ui.common.extension

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

@ExperimentalFoundationApi
fun <T : Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(
        count = lazyPagingItems.itemCount,
        key = if (key == null) {
            null
        } else { index ->
            lazyPagingItems.peek(index)
                ?.let { key(it) }
                ?: index
        }
    ) { index ->
        itemContent(lazyPagingItems[index])
    }
}
