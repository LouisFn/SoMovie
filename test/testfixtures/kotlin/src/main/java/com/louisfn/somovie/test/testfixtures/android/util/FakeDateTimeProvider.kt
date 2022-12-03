package com.louisfn.somovie.test.testfixtures.android.util

import com.louisfn.somovie.common.provider.DateTimeProvider
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class FakeDateTimeProvider : DateTimeProvider {

    var localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0)

    override fun now(): Instant = localDateTime.toInstant(ZoneOffset.UTC)
}
