package com.louisfn.somovie.app.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.louisfn.somovie.ui.common.LocalAppRouter
import com.louisfn.somovie.ui.common.LocalMoshi
import com.louisfn.somovie.ui.common.base.BaseActivity
import com.louisfn.somovie.ui.common.navigation.AppRouter
import com.louisfn.somovie.ui.theme.AppTheme
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : BaseActivity() {

    @Inject
    internal lateinit var moshi: Moshi

    @Inject
    internal lateinit var appRouter: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            CompositionLocalProvider(
                LocalMoshi provides moshi,
                LocalAppRouter provides appRouter,
            ) {
                AppTheme {
                    val systemUiController = rememberSystemUiController()
                    SideEffect {
                        systemUiController.setSystemBarsColor(Color.Transparent)
                    }

                    MainScreen()
                }
            }
        }
    }
}
