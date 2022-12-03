plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.ui.common"

    buildFeatures {
        compose = true
    }
}

dependencies {
    compose()
    paging()
    hilt()
    coil()
    moshi()

    implementation(project(":common"))
    implementation(project(":domain:util"))
    implementation(project(":domain:model"))
    implementation(project(":domain:exception"))

    implementation(Libraries.androidCoreKtx)
    implementation(Libraries.activity)
    implementation(Libraries.Lifecycle.runtime)
    implementation(Libraries.Coroutines.android)
    implementation(Libraries.timber)
}
