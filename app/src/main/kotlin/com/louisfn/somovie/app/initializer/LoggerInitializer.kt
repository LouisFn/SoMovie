package com.louisfn.somovie.app.initializer

import android.app.Application
import com.louisfn.somovie.core.logger.LogAdapter
import com.louisfn.somovie.core.logger.Logger
import javax.inject.Inject

internal class LoggerInitializer @Inject constructor(
    private val logAdapter: LogAdapter,
) : AppInitializer {

    override fun onCreate(application: Application) {
        logAdapter.setup()

        Logger.adapter = logAdapter
    }
}
