package com.louisfn.somovie.ui.common

import androidx.compose.runtime.staticCompositionLocalOf
import com.louisfn.somovie.ui.common.navigation.AppRouter
import com.squareup.moshi.Moshi

val LocalMoshi = staticCompositionLocalOf<Moshi> {
    error("Moshi not provided")
}

val LocalAppRouter = staticCompositionLocalOf<AppRouter> {
    error("AppRouter not provided")
}
