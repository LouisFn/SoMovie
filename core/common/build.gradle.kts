plugins {
    id(Plugins.SOMOVIE_KOTLIN_LIBRARY)
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    moshi(versionCatalog)

    implementation(libs.coroutines.android)
    implementation(libs.hilt.core)

    kapt(libs.hilt.android.compiler)
}
