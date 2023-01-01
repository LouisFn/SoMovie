package com.louisfn.somovie.core.common.extension

fun <T> MutableList<T>.addIfAbsent(item: T) {
    if (!contains(item)) {
        add(item)
    }
}
