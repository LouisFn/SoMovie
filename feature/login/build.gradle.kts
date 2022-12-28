plugins {
    id(Plugins.SOMOVIE_ANDROID_FEATURE)
}

android {
    namespace = "com.louisfn.somovie.feature.login"
}

dependencies {
    implementation(libs.accompanist.webview)
}
