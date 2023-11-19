package plugin

import AppConfig
import AppConfig.KEYSTORE_PROPERTIES_FILE_NAME
import Plugins
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import plugin.util.configureAndroid
import plugin.util.configureKotlinOptions
import java.util.Properties

@Suppress("UnstableApiUsage")
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(Plugins.ANDROID_APPLICATION)
                apply(Plugins.KOTLIN_ANDROID)
                apply(Plugins.KAPT)
                apply(Plugins.KSP)
                apply(Plugins.KOTLIN_PARCELIZE)
                apply(Plugins.SOMOVIE_JACOCO)
            }

            configureKotlinOptions()

            extensions.configure<ApplicationExtension> {
                configureAndroid(this)

                defaultConfig {
                    applicationId = AppConfig.APPLICATION_ID
                    targetSdk = AppConfig.TARGET_SDK_VERSION
                    versionCode = AppConfig.VERSION_CODE
                    versionName = AppConfig.VERSION_NAME
                }

                signingConfigs {
                    create(AppConfig.RELEASE_BUILD_TYPE) {
                        if (rootProject.file(KEYSTORE_PROPERTIES_FILE_NAME).canRead()) {
                            val properties = getKeystoreProperties()
                            keyAlias = properties.getProperty("keyAlias")
                            keyPassword = properties.getProperty("keyPassword")
                            storeFile = file(properties.getProperty("storeFile"))
                            storePassword = properties.getProperty("storePassword")
                        } else {
                            println(
                                """
                       Cannot create a release signing config.
                       The file, $KEYSTORE_PROPERTIES_FILE_NAME, either does not exist or cannot be read from.
                                """.trimIndent(),
                            )
                        }
                    }
                }

                buildTypes {
                    getByName(AppConfig.RELEASE_BUILD_TYPE) {
                        signingConfig = signingConfigs.getByName(AppConfig.RELEASE_BUILD_TYPE)
                        isMinifyEnabled = true
                        isShrinkResources = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }
                }
            }
        }
    }
}

private fun Project.getKeystoreProperties() = Properties().apply {
    load(rootProject.file(KEYSTORE_PROPERTIES_FILE_NAME).inputStream())
}
