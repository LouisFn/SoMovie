plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.louisfn.somovie.test.shared.android"

    buildFeatures {
        compose = true
    }
}

dependencies {
    compose()
    paging()

    implementation(Libraries.activity)
    implementation(Libraries.Coroutines.test)
    implementation(Libraries.AndroidTest.runner)
    implementation(Libraries.Hilt.test)
}
