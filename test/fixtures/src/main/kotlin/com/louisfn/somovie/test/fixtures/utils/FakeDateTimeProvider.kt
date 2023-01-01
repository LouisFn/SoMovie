package com.louisfn.somovie.test.fixtures.utils

import com.louisfn.somovie.core.common.provider.DateTimeProvider
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class FakeDateTimeProvider : DateTimeProvider {

    var localDateTime = LocalDateTime.of(2022, 1, 1, 0, 0)

    override fun now(): Instant = localDateTime.toInstant(ZoneOffset.UTC)
}
