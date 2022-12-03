plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.ksp)
}

android {
    namespace = "com.louisfn.somovie.data.mapper"
}

dependencies {
    implementation(project(":common"))
    implementation(project(":data:database"))
    implementation(project(":data:datastore"))
    implementation(project(":data:network"))
    implementation(project(":domain:model"))

    implementation(Libraries.annotation)
    implementation(Libraries.Hilt.core)
}
