plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.home.watchlist"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()
    paging()

    implementation(project(":feature:home:common"))
    implementation(project(":feature:login"))
}
