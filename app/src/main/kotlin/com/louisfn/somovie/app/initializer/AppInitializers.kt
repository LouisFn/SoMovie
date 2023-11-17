package com.louisfn.somovie.app.initializer

import android.app.Application
import javax.inject.Inject

internal class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>,
) : AppInitializer {

    override fun onCreate(application: Application) {
        initializers.forEach { it.onCreate(application) }
    }

    override fun onBackground() {
        initializers.forEach { it.onBackground() }
    }

    override fun onForeground() {
        initializers.forEach { it.onForeground() }
    }
}
