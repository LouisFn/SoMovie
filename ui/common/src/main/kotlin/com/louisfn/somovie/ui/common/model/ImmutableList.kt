package com.louisfn.somovie.ui.common.model

import androidx.compose.runtime.Immutable

@Immutable
data class ImmutableList<out E>(
    private val internalList: List<E> = emptyList()
) : List<E> by internalList {

    operator fun plus(list: ImmutableList<@UnsafeVariance E>): ImmutableList<E> =
        ImmutableList(internalList + list)

    operator fun plus(item: @UnsafeVariance E): ImmutableList<E> =
        ImmutableList(internalList + item)
}
