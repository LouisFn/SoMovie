package com.louisfn.somovie.core.common.extension

fun String.takeIfNotBlank(): String? = this.takeIf { it.isNotBlank() }
