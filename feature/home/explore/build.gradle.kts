plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.home.explore"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()

    implementation(project(":feature:home:common"))
}
