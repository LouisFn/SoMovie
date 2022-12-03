plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.ksp)
    kotlin(Plugins.kapt)
}

android {
    namespace = "com.louisfn.somovie.data.datastore"
}

dependencies {
    moshi()

    implementation(project(":common"))

    implementation(Libraries.Datastore.typed)
    implementation(Libraries.Hilt.core)
    implementation(Libraries.Hilt.android)
    kapt(Libraries.Hilt.compiler)
    implementation(Libraries.Coroutines.android)
}
