plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.home.settings"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()

    implementation(project(":feature:home:common"))
    implementation(project(":feature:login"))

    implementation(Libraries.Lifecycle.process)
    implementation(Libraries.Accompanist.webView)
}
