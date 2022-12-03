package com.louisfn.somovie.ui.common.extension

import android.content.Context
import com.louisfn.somovie.common.extension.toMinutesPartCompat
import java.time.Duration
import com.louisfn.somovie.ui.common.R as commonR

fun Duration.toRuntimeString(context: Context): String =
    buildString {
        val hours = toHours().toInt()
        val minutes = toMinutesPartCompat().toInt()
        if (hours > 0) {
            append("${toHours()} ${context.resources.getQuantityString(commonR.plurals.common_hrs_abbr, hours)} ")
        }
        if (minutes > 0) {
            append("$minutes ${context.resources.getQuantityString(commonR.plurals.common_mins_abbr, minutes)}")
        }
    }
