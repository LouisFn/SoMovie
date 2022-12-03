package com.louisfn.somovie.common.extension

fun <T> MutableList<T>.addIfAbsent(item: T) {
    if (!contains(item)) {
        add(item)
    }
}
