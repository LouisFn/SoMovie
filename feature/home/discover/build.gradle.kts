plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.home.discover"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()
}
