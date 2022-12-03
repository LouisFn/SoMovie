plugins {
    id(Plugins.kotlin)
}

dependencies {
    implementation(project(":domain:model"))
    implementation(project(":domain:repository"))
    implementation(project(":domain:exception"))

    implementation(Libraries.jUnit)
    implementation(Libraries.Coroutines.test)
    implementation(Libraries.Paging.common)
    implementation(Libraries.faker)
}
