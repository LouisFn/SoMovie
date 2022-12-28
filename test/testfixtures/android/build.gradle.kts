plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.test.testfixtures.android"
}

dependencies {
    implementation(project(":test:shared:kotlin"))
    implementation(project(":test:shared:android"))
    implementation(project(":test:testfixtures:kotlin"))
    implementation(project(":common"))
    implementation(project(":data:network"))
    implementation(project(":data:database"))
    implementation(project(":data:datastore"))
    implementation(project(":data:mapper"))
    implementation(project(":ui:common"))
    implementation(project(":feature:login"))
    implementation(libs.coroutines.test)
    implementation(libs.androidx.paging.common)
    implementation(libs.faker)
}
