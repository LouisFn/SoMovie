@file:Suppress("MissingPackageDeclaration")

object AppConfig {
    const val MIN_SDK_VERSION = 26
    const val COMPILE_SDK_VERSION = 34
    const val TARGET_SDK_VERSION = 34

    const val VERSION_CODE = 1
    const val VERSION_NAME = "0.1.0"

    const val APPLICATION_ID = "com.louisfn.somovie"

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    const val DEBUG_BUILD_TYPE = "debug"
    const val RELEASE_BUILD_TYPE = "release"

    const val KEYSTORE_PROPERTIES_FILE_NAME = "keystore.properties"
    const val LOCAL_PROPERTIES_FILE_NAME = "local.properties"
}
