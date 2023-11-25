import java.util.Properties

plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.data.network"

    defaultConfig {
        buildConfigField(
            "String",
            "API_KEY",
            "\"${getLocalProperties()?.getProperty("tmdb_api_key")}\"",
        )
        buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/3/\"")
    }
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    moshi(versionCatalog)

    implementation(project(":core:common"))
    implementation(project(":core:logger"))
    implementation(project(":data:datastore"))
    implementation(project(":domain:model"))
    implementation(project(":domain:exception"))
    implementation(libs.androidx.annotation)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshiConverter)
    implementation(libs.hilt.core)
    implementation(libs.coroutines.android)
    implementation(libs.okhttp.logginInterceptor)
    debugImplementation(libs.flipper.network)

    kapt(libs.hilt.android.compiler)
}

fun getLocalProperties(): Properties? =
    try {
        Properties().apply {
            load(rootProject.file(AppConfig.LOCAL_PROPERTIES_FILE_NAME).inputStream())
        }
    } catch (e: Exception) {
        println("Cannot load ${AppConfig.LOCAL_PROPERTIES_FILE_NAME} $e")
        null
    }
