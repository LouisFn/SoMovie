plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.domain.usecase"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":domain:model"))
    implementation(project(":data:repository"))

    implementation(libs.hilt.core)
    implementation(libs.androidx.paging.common)
    implementation(libs.coroutines.android)
}
