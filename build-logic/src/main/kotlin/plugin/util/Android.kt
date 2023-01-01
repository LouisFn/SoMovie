package plugin.util

import AppConfig
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *>
) {
    commonExtension.apply {
        compileSdk = AppConfig.compileSdkVersion

        defaultConfig {
            minSdk = AppConfig.minSdkVersion
            testInstrumentationRunner = AppConfig.testInstrumentationRunner
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
}
