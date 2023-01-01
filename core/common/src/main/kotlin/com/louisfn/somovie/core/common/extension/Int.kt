package com.louisfn.somovie.core.common.extension

import kotlin.math.sign

fun Int.withSign(value: Int) = this * value.sign

fun Int.withSign(value: Float) = this * value.sign
