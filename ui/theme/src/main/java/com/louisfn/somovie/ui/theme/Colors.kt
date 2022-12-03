package com.louisfn.somovie.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val MaterialColorPalette = darkColors(
    primary = AppColor.Tuna,
    surface = AppColor.Tuna,
    background = AppColor.MineShaft,
    secondary = AppColor.PictonBlue,
    onSecondary = Color.White,
    onPrimary = Color.White,
    onSurface = Color.White,
    onBackground = Color.White,
    error = Color.Red,
    onError = Color.White
)

val CustomColorPalette = CustomColors(
    placeholder = AppColor.Tuna
)
