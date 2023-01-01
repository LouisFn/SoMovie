package com.louisfn.somovie.test.shared

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import kotlin.random.Random

object FakeInstantFactory {

    fun create(
        from: Instant = Instant.ofEpochMilli(0),
        to: Instant = Instant.now()
    ): Instant = Instant.ofEpochMilli(Random.nextLong(from.toEpochMilli(), to.toEpochMilli()))
}

object FakeDurationFactory {

    fun create(
        from: Duration = Duration.ofHours(0),
        to: Duration = Duration.ofHours(24)
    ): Duration = Duration.ofMillis(Random.nextLong(from.toMillis(), to.toMillis()))
}

object FakeLocalDateFactory {

    fun create(
        from: LocalDate = LocalDate.ofEpochDay(0),
        to: LocalDate = LocalDate.now()
    ): LocalDate = LocalDate.ofEpochDay(Random.nextLong(from.toEpochDay(), to.toEpochDay()))
}
