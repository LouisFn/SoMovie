plugins {
    id(Plugins.SOMOVIE_KOTLIN_LIBRARY)
}

dependencies {
    implementation(project(":domain:model"))

    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.annotation)
    implementation(libs.coroutines.android)
}
