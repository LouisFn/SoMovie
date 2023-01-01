plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.data.repository"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    test(versionCatalog)

    implementation(project(":core:common"))
    implementation(project(":core:logger"))
    implementation(project(":data:network"))
    implementation(project(":data:mapper"))
    implementation(project(":data:datastore"))
    implementation(project(":data:database"))
    implementation(project(":domain:model"))
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.hilt.core)
    implementation(libs.coroutines.android)

    kapt(libs.hilt.android.compiler)

    testImplementation(project(":test:fixtures"))
    testImplementation(project(":test:shared"))
}
