plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.home.container"
}

dependencies {
    implementation(project(":feature:home:common"))
    implementation(project(":feature:home:discover"))
    implementation(project(":feature:home:watchlist"))
    implementation(project(":feature:home:explore"))
    implementation(project(":feature:home:account"))
}
