package plugin

import AppConfig
import Plugins
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import plugin.util.configureAndroid
import plugin.util.configureKotlinOptions

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.ANDROID_LIBRARY)
                apply(Plugins.KOTLIN_ANDROID)
                apply(Plugins.KAPT)
                apply(Plugins.KSP)
                apply(Plugins.SOMOVIE_JACOCO)
            }

            configureKotlinOptions()
            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = AppConfig.TARGET_SDK_VERSION
                configureAndroid(this)
            }

            extensions.configure<TestedExtension> {
                (project.findProperty("testBuildType") as? String)?.let {
                    testBuildType = it
                }
            }
        }
    }
}
