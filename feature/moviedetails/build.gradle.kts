plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.moviedetails"
}

dependencies {
    implementation(libs.accompanist.flowlayout)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
}
