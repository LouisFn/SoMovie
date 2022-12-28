plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.data.repository"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    test(versionCatalog)

    implementation(project(":common"))
    implementation(project(":data:network"))
    implementation(project(":data:mapper"))
    implementation(project(":data:datastore"))
    implementation(project(":data:database"))
    implementation(project(":domain:model"))
    implementation(project(":domain:repository"))
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.hilt.core)
    implementation(libs.coroutines.android)

    kapt(libs.hilt.android.compiler)

    testImplementation(project(":test:testfixtures:android"))
    testImplementation(project(":test:testfixtures:kotlin"))
    testImplementation(project(":test:shared:android"))
}
