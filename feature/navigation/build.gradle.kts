plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.navigation"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    paging(versionCatalog)

    implementation(project(":feature:home:container"))
    implementation(project(":feature:moviedetails"))
    implementation(project(":feature:movielist"))
    implementation(libs.androidx.navigation.compose)
}
