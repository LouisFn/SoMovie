plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.home.explore"
}

dependencies {
    implementation(project(":feature:home:common"))
}
