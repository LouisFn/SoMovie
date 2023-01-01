package com.louisfn.somovie.test.shared.paging

import androidx.paging.DifferCallback
import androidx.paging.NullPaddedList
import androidx.paging.PagingData
import androidx.paging.PagingDataDiffer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class PagingItemsCollector<T : Any>(val dispatcher: TestDispatcher = UnconfinedTestDispatcher()) {

    private val _items = MutableStateFlow<List<T>?>(null)
    val items: StateFlow<List<T>?> = _items

    private val pagingDataDiffer = object : PagingDataDiffer<T>(NoopListCallback(), dispatcher) {
        override suspend fun presentNewList(
            previousList: NullPaddedList<T>,
            newList: NullPaddedList<T>,
            lastAccessedIndex: Int,
            onListPresentable: () -> Unit
        ): Int? {
            onListPresentable()
            _items.value = snapshot().items
            return null
        }
    }

    suspend fun collectFrom(pagingData: PagingData<T>) {
        pagingDataDiffer.collectFrom(pagingData)
    }

    class NoopListCallback : DifferCallback {
        override fun onChanged(position: Int, count: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }
}
