plugins {
    id(Plugins.kotlin)
}

dependencies {
    implementation(project(":common"))
    api(project(":domain:repository"))
    implementation(project(":domain:model"))
    implementation(project(":domain:exception"))
    implementation(project(":test:shared:kotlin"))

    implementation(Libraries.Coroutines.test)
    implementation(Libraries.Paging.common)
    implementation(Libraries.faker)
}
