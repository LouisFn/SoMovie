plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.home.container"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()

    implementation(project(":feature:home:common"))
    implementation(project(":feature:home:discover"))
    implementation(project(":feature:home:watchlist"))
    implementation(project(":feature:home:explore"))
    implementation(project(":feature:home:account"))
}
