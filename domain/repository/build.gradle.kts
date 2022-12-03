plugins {
    id(Plugins.kotlin)
    kotlin(Plugins.kapt)
}

dependencies {
    implementation(project(":domain:model"))

    implementation(Libraries.Paging.common)
    implementation(Libraries.annotation)
    implementation(Libraries.Coroutines.android)
}
