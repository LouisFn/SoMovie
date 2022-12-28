plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.movielist"
}

dependencies {
    val versionCatalog = getLibsVersionCatalog()
    paging(versionCatalog)
}
