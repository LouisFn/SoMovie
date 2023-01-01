plugins {
    id(Plugins.SOMOVIE_ANDROID_LIBRARY)
}

android {
    namespace = "com.louisfn.somovie.test.fixtures"
}

dependencies {
    api(project(":data:repository"))

    implementation(project(":core:common"))
    implementation(project(":data:database"))
    implementation(project(":data:datastore"))
    implementation(project(":data:mapper"))
    implementation(project(":data:network"))
    implementation(project(":domain:model"))
    implementation(project(":feature:login"))
    implementation(project(":test:shared"))
    implementation(project(":ui:common"))
    implementation(libs.coroutines.test)
    implementation(libs.androidx.paging.common)
    implementation(libs.faker)
}
