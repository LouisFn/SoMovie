package com.louisfn.somovie.app.initializer

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import javax.inject.Inject

internal class FlipperInitializer @Inject constructor(
    private val networkFlipperPlugin: NetworkFlipperPlugin,
    private val inspectorFlipperPlugin: InspectorFlipperPlugin,
    private val crashReporterPlugin: CrashReporterPlugin
) : AppInitializer {

    override fun onCreate(application: Application) {
        SoLoader.init(application, false)

        if (FlipperUtils.shouldEnableFlipper(application)) {
            AndroidFlipperClient.getInstance(application).apply {
                addPlugin(networkFlipperPlugin)
                addPlugin(inspectorFlipperPlugin)
                addPlugin(crashReporterPlugin)
                start()
            }
        }
    }
}
