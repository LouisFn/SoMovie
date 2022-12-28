plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.data.datastore"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    moshi(versionCatalog)

    implementation(project(":common"))
    implementation(libs.androidx.datastore.core)
    implementation(libs.hilt.core)
    implementation(libs.hilt.android)
    implementation(libs.coroutines.android)

    kapt(libs.hilt.android.compiler)
}
