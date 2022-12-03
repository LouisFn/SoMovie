package com.louisfn.somovie.test.shared.android.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import java.util.concurrent.atomic.AtomicBoolean

class FakePagingSource<T : Any>(
    private val items: StateFlow<List<T>>,
    private val scope: TestScope
) : PagingSource<Int, T>() {
    private val registeredObserver: AtomicBoolean = AtomicBoolean(false)

    override fun getRefreshKey(state: PagingState<Int, T>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        if (registeredObserver.compareAndSet(false, true)) {
            scope.launch(start = CoroutineStart.UNDISPATCHED) {
                items
                    .drop(1)
                    .collect { invalidate() }
            }
        }

        val offset = when (params) {
            is LoadParams.Refresh -> 0
            is LoadParams.Append -> params.key
            else -> throw NotImplementedError("$params is not implemented")
        }

        val limit = when (params) {
            is LoadParams.Refresh -> params.key ?: params.loadSize
            is LoadParams.Append -> params.loadSize
            else -> throw NotImplementedError("$params is not implemented")
        }

        val data = items.value.drop(offset).take(limit)
        val nextKey = if (data.isNotEmpty()) offset + limit else null
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = nextKey
        )
    }
}
