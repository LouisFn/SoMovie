package com.louisfn.somovie.common.moshi.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE

internal object LocalDateAdapter {

    @ToJson
    fun toJson(date: LocalDate?): String? = date?.format(ISO_LOCAL_DATE)

    @FromJson
    fun fromJson(json: String?): LocalDate? = json
        ?.takeIf { it.isNotBlank() }
        ?.let { LocalDate.parse(json, ISO_LOCAL_DATE) }
}
