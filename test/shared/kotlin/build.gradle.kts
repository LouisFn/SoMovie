plugins {
    id(Plugins.SOMOVIE_KOTLIN_LIBRARY)
}

dependencies {
    implementation(project(":domain:model"))
    implementation(project(":domain:repository"))
    implementation(project(":domain:exception"))

    implementation(libs.junit)
    implementation(libs.coroutines.test)
    implementation(libs.androidx.paging.common)
    implementation(libs.faker)
}
