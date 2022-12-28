plugins {
    id(Plugins.SOMOVIE_KOTLIN_LIBRARY)
}

dependencies {
    implementation(project(":common"))
    implementation(project(":domain:model"))
    implementation(project(":domain:repository"))

    implementation(libs.hilt.core)
    implementation(libs.androidx.paging.common)
    implementation(libs.coroutines.android)
}
