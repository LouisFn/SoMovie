plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.movielist"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()
    paging()
}
