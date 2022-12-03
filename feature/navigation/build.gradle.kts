plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.feature.navigation"

    buildFeatures {
        compose = true
    }
}

dependencies {
    feature()
    paging()

    implementation(project(":feature:home:container"))
    implementation(project(":feature:moviedetails"))
    implementation(project(":feature:movielist"))

    implementation(Libraries.navigation)
}
