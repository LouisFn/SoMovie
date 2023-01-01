plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.core.logger"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    moshi(versionCatalog)

    implementation(libs.coroutines.android)
    implementation(libs.hilt.core)
    implementation(libs.timber)

    kapt(libs.hilt.android.compiler)
}
