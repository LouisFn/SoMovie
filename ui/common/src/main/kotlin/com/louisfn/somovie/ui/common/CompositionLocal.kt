package com.louisfn.somovie.ui.common

import androidx.compose.runtime.staticCompositionLocalOf
import com.louisfn.somovie.ui.common.navigation.AppRouter

val LocalAppRouter = staticCompositionLocalOf<AppRouter> {
    error("AppRouter not provided")
}
