plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.login"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()

    implementation(Libraries.Accompanist.webView)
}
