package com.louisfn.somovie.common.extension

fun String.takeIfNotBlank(): String? = this.takeIf { it.isNotBlank() }
