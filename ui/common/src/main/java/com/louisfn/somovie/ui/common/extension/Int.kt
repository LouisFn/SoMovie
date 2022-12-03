package com.louisfn.somovie.ui.common.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import java.text.NumberFormat
import java.util.*

fun Int.toDollarFormat() = NumberFormat.getCurrencyInstance().let {
    it.currency = Currency.getInstance("USD")
    it.maximumFractionDigits = 0
    it.format(this)
}

@Composable
fun Float.pxToDp() = pxToDp(LocalDensity.current)

@Composable
fun Int.pxToDp() = pxToDp(LocalDensity.current)

fun Float.pxToDp(density: Density) = with(density) { this@pxToDp.toDp() }

fun Int.pxToDp(density: Density) = with(density) { this@pxToDp.toDp() }
