plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.core.imageurlprovider"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    test(versionCatalog)

    implementation(project(":core:common"))
    implementation(project(":domain:model"))
    implementation(project(":data:repository"))
    implementation(libs.hilt.core)
    implementation(libs.androidx.paging.common)
    implementation(libs.coroutines.android)

    testImplementation(project(":test:fixtures"))
}
