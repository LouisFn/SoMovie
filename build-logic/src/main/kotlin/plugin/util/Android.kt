package plugin.util

import AppConfig
import androidTestImplementation
import com.android.build.api.dsl.CommonExtension
import getLibsVersionCatalog
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
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
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
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
    dependencies {
        val libs = getLibsVersionCatalog()
        androidTestImplementation(libs.findLibrary("androidx.test.runner").get())
    }
}
