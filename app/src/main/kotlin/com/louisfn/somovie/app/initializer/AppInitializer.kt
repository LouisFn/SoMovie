package com.louisfn.somovie.app.initializer

import android.app.Application
import androidx.annotation.UiThread

internal interface AppInitializer {

    @UiThread
    fun onCreate(application: Application) {}

    @UiThread
    fun onBackground() {}

    @UiThread
    fun onForeground() {}
}
