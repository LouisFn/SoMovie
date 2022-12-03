package com.louisfn.somovie.ui.common.error

import java.util.concurrent.TimeUnit.SECONDS

interface Error {
    val duration: Long
}

data class SimpleMessageError(
    val message: String,
    override val duration: Long = SECONDS.toMillis(DEFAULT_DURATION_IN_SEC)
) : Error {

    companion object {
        private const val DEFAULT_DURATION_IN_SEC = 5L
    }
}
