plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.home.watchlist"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    paging(versionCatalog)

    implementation(project(":feature:home:common"))
    implementation(project(":feature:login"))
}
