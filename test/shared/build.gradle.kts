plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
    id(Plugins.SOMOVIE_ANDROID_COMPOSE)
}

android {
    namespace = "com.louisfn.somovie.test.shared"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    paging(versionCatalog)

    implementation(project(":domain:model"))
    implementation(project(":domain:exception"))

    implementation(libs.junit)
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.activity.compose)
    implementation(libs.coroutines.test)
    implementation(libs.androidx.test.runner)
    implementation(libs.hilt.android.testing)
    implementation(libs.faker)
}
