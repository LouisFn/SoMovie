package plugin.util

import AppConfig
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = AppConfig.COMPILE_SDK_VERSION

        defaultConfig {
            minSdk = AppConfig.MIN_SDK_VERSION
            testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
        }

        buildFeatures {
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        buildTypes {
            getByName(AppConfig.DEBUG_BUILD_TYPE) {
                enableUnitTestCoverage = true
                enableAndroidTestCoverage = true
            }
            getByName(AppConfig.RELEASE_BUILD_TYPE) {
                enableUnitTestCoverage = true
                enableAndroidTestCoverage = true
            }
        }
    }
}
