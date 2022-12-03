plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.ui.theme"

    buildFeatures {
        compose = true
    }
}

dependencies {
    compose()
}
