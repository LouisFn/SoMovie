@file:Suppress("PropertyName", "VariableNaming")

import java.util.Properties

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.hilt)
    id(Plugins.kotlinParcelize)
    id(Plugins.ksp)
    kotlin(Plugins.kapt)
}

val KEYSTORE_FILE_NAME = "keystore.properties"

android {
    namespace = "com.louisfn.somovie"

    defaultConfig {
        applicationId = AppConfig.applicationId
    }

    signingConfigs {
        create(AppConfig.RELEASE_BUILD_TYPE) {
            if (rootProject.file(KEYSTORE_FILE_NAME).canRead()) {
                val properties = getKeystoreProperties()
                keyAlias = properties.getProperty("keyAlias")
                keyPassword = properties.getProperty("keyPassword")
                storeFile = file(properties.getProperty("storeFile"))
                storePassword = properties.getProperty("storePassword")
            } else {
                println(
                    """
                       Cannot create a release signing config.
                       The file, $KEYSTORE_FILE_NAME, either does not exist or cannot be read from.
                    """.trimIndent()
                )
            }
        }
    }

    buildTypes {
        getByName(AppConfig.DEBUG_BUILD_TYPE) {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        getByName(AppConfig.RELEASE_BUILD_TYPE) {
            signingConfig = signingConfigs.getByName(AppConfig.RELEASE_BUILD_TYPE)
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    compose()
    hilt()
    coil()
    flipper()
    moshi()

    implementation(project(":common"))
    implementation(project(":domain:model"))
    implementation(project(":domain:usecase"))
    implementation(project(":domain:exception"))
    implementation(project(":domain:util"))
    implementation(project(":data:network"))
    implementation(project(":data:repository"))
    implementation(project(":ui:common"))
    implementation(project(":ui:component"))
    implementation(project(":ui:theme"))
    implementation(project(":feature:navigation"))
    implementation(project(":feature:home:container"))

    implementation(Libraries.androidCoreKtx)
    implementation(Libraries.activity)
    implementation(Libraries.Lifecycle.process)
    implementation(Libraries.Retrofit.core)

    implementation(Libraries.Accompanist.systemuicontroller)
    implementation(Libraries.Accompanist.insets)
    implementation(Libraries.Accompanist.insetsui)

    implementation(Libraries.timber)

    debugImplementation(Libraries.leakcanary)
}

kapt {
    correctErrorTypes = true
}

fun getKeystoreProperties() = Properties().apply {
    load(rootProject.file(KEYSTORE_FILE_NAME).inputStream())
}
