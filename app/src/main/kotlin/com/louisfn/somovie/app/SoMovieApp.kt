package com.louisfn.somovie.app

import android.app.Application
import androidx.annotation.UiThread
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.louisfn.somovie.app.initializer.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
internal class SoMovieApp : Application(), LifecycleObserver {

    @Inject
    internal lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()

        initializers.onCreate(this)

        ProcessLifecycleOwner.get().lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    onForeground()
                }

                override fun onStop(owner: LifecycleOwner) {
                    onBackground()
                }
            }
        )
    }

    @UiThread
    private fun onForeground() {
        initializers.onForeground()
    }

    @UiThread
    private fun onBackground() {
        initializers.onBackground()
    }
}
