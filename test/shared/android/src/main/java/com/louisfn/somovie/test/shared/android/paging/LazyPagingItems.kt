package com.louisfn.somovie.test.shared.android.paging

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf

@Composable
@Suppress("UnstableCollections")
fun <I : Any> createLazyPagingItems(items: List<I>): LazyPagingItems<I> =
    flowOf(PagingData.from(items)).collectAsLazyPagingItems()
