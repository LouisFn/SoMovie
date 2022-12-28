plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
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

    implementation(libs.androidx.annotation)
    implementation(libs.hilt.core)
}
