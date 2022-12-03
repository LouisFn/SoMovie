package com.louisfn.somovie.app.initializer

import android.app.Application
import com.louisfn.somovie.common.logger.LogAdapter
import com.louisfn.somovie.common.logger.Logger
import javax.inject.Inject

internal class LoggerInitializer @Inject constructor(
    private val logAdapter: LogAdapter
) : AppInitializer {

    override fun onCreate(application: Application) {
        logAdapter.setup()

        Logger.adapter = logAdapter
    }
}
