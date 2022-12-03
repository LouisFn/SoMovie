package com.louisfn.somovie.ui.common.extension

import androidx.annotation.AnyThread
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@AnyThread
fun LocalDate.toReleaseFormat() = format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
