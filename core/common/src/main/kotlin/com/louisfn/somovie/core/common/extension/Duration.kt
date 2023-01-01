@file:Suppress("MagicNumber")

package com.louisfn.somovie.core.common.extension

import java.time.Duration

fun Duration.toHoursPartCompat() = toHours() % 24

fun Duration.toMinutesPartCompat() = toMinutes() % 60

fun Duration.toSecondPartCompat() = seconds % 60
