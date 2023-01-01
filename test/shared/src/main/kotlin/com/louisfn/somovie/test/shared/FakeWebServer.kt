package com.louisfn.somovie.test.shared

import com.louisfn.somovie.domain.exception.NoNetworkException
import kotlinx.coroutines.delay
import java.io.IOException

class FakeWebServer(
    var isNetworkAvailable: Boolean = true,
    var responseDuration: Long = 0L
) {

    suspend fun execute() {
        delay(responseDuration)
        throwIfNoNetwork()
    }

    private fun throwIfNoNetwork() {
        if (!isNetworkAvailable) {
            throw NoNetworkException(IOException())
        }
    }
}
