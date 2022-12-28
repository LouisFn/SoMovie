plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
    id(Plugins.SOMOVIE_ANDROID_COMPOSE)
    id(Plugins.SOMOVIE_ANDROID_HILT)
}

android {
    namespace = "com.louisfn.somovie.ui.common"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    paging(versionCatalog)
    coil(versionCatalog)
    moshi(versionCatalog)

    implementation(project(":common"))
    implementation(project(":domain:util"))
    implementation(project(":domain:model"))
    implementation(project(":domain:exception"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.coroutines.android)
    implementation(libs.timber)
}
