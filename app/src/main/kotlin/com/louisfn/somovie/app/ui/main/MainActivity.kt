package com.louisfn.somovie.app.ui.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.louisfn.somovie.ui.common.LocalAppRouter
import com.louisfn.somovie.ui.common.base.BaseActivity
import com.louisfn.somovie.ui.common.navigation.AppRouter
import com.louisfn.somovie.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : BaseActivity() {

    @Inject
    internal lateinit var appRouter: AppRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalAppRouter provides appRouter,
            ) {
                AppTheme {
                    MainScreen()
                }
            }
        }
    }
}
