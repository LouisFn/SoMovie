plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.ui.component"

    buildFeatures {
        compose = true
    }
}

dependencies {
    compose()
    coil()

    implementation(project(":common"))
    implementation(project(":domain:model"))
    implementation(project(":ui:common"))
    implementation(project(":ui:theme"))

    implementation(Libraries.Accompanist.insetsui)
}
