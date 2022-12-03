package com.louisfn.somovie.common.extension

import java.time.Instant

fun Instant?.isExpired(now: Instant, timeout: Long): Boolean =
    this?.toEpochMilli()?.let { now.toEpochMilli() - it > timeout } ?: true
