import java.util.Properties

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.ksp)
    kotlin(Plugins.kapt)
}

android {
    namespace = "com.louisfn.somovie.data.network"

    defaultConfig {
        buildConfigField(
            "String",
            "API_KEY",
            "\"${getLocalProperties()?.getProperty("tmdb_api_key")}\""
        )
        buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/3/\"")
    }
}

dependencies {
    moshi()

    implementation(project(":common"))
    implementation(project(":data:datastore"))
    implementation(project(":domain:model"))
    implementation(project(":domain:exception"))

    implementation(Libraries.annotation)
    implementation(Libraries.Retrofit.core)
    implementation(Libraries.Retrofit.moshiConverter)

    implementation(Libraries.Hilt.core)
    kapt(Libraries.Hilt.compiler)
    implementation(Libraries.Flipper.network)
    implementation(Libraries.Coroutines.android)
    implementation(Libraries.okHttpLoggingInterceptor)
}

fun getLocalProperties(): Properties? =
    try {
        Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
    } catch (e: Exception) {
        println("Cannot load local.properties $e")
        null
    }
