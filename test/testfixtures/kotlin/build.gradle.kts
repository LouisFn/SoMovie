plugins {
    id(Plugins.SOMOVIE_KOTLIN_LIBRARY)
}

dependencies {
    implementation(project(":common"))
    api(project(":domain:repository"))
    implementation(project(":domain:model"))
    implementation(project(":domain:exception"))
    implementation(project(":test:shared:kotlin"))

    implementation(libs.coroutines.test)
    implementation(libs.androidx.paging.common)
    implementation(libs.faker)
}
