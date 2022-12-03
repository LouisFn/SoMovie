package com.louisfn.somovie.data.repository.paging

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T : Any, R : Any> Flow<PagingData<T>>.mapPaging(
    crossinline transform: suspend (value: T) -> R
): Flow<PagingData<R>> =
    map { pagingData -> pagingData.map { transform(it) } }
