plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.moviedetails"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()
    coil()

    implementation(Libraries.Accompanist.flowLayout)
    implementation(Libraries.Accompanist.pager)
    implementation(Libraries.Accompanist.pagerIndicators)
}
