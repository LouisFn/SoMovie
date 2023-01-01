package com.louisfn.somovie.core.common.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

internal object OffsetDateTimeAdapter {

    @FromJson
    fun fromJson(json: String): OffsetDateTime =
        OffsetDateTime.parse(json, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    @ToJson
    fun tojson(offsetDateTime: OffsetDateTime): String =
        offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}
