plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
    id(Plugins.SOMOVIE_ANDROID_COMPOSE)
}

android {
    namespace = "com.louisfn.somovie.test.shared.android"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    paging(versionCatalog)

    implementation(libs.androidx.activity.compose)
    implementation(libs.coroutines.test)
    implementation(libs.androidx.test.runner)
    implementation(libs.hilt.android.testing)
}
