plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.home.settings"
}

dependencies {
    implementation(project(":feature:home:common"))
    implementation(project(":feature:login"))
    implementation(libs.androidx.lifecycle.process)
}
